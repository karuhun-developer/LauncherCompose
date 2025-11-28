/*
 * Copyright 2025 The Karuhun Developer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.karuhun.feature.screensaver.ui.component

import android.content.Context
import androidx.annotation.OptIn
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.karuhun.feature.screensaver.ui.cache.VideoCacheManager
import android.util.Log

@OptIn(UnstableApi::class)
@Composable
fun VideoPlayer(
    modifier: Modifier = Modifier,
    videoUri: String,
    videoCacheManager: VideoCacheManager? = null,
    isPlaying: Boolean = true,
    isMuted: Boolean = false,
    onPlayerReady: (ExoPlayer) -> Unit = {},
    onError: (String) -> Unit = {},
    onBufferingChanged: (Boolean) -> Unit = {},
) {
    val context = LocalContext.current

    Log.d("VideoPlayer", "VideoPlayer composing with uri: $videoUri, isPlaying: $isPlaying")

    val exoPlayer = remember(videoCacheManager) {
        createExoPlayer(
            context = context,
            videoCacheManager = videoCacheManager,
            onError = onError,
            onBufferingChanged = onBufferingChanged,
        )
    }

    LaunchedEffect(videoUri) {
        if (videoUri.isNotEmpty()) {
            try {
                Log.d("VideoPlayer", "Loading video: $videoUri")
                val mediaItem = MediaItem.fromUri(videoUri)
                exoPlayer.setMediaItem(mediaItem)
                exoPlayer.prepare()
                Log.d("VideoPlayer", "Video prepared successfully")
                onPlayerReady(exoPlayer)
            } catch (e: Exception) {
                Log.e("VideoPlayer", "Failed to load video", e)
                onError("Failed to load video: ${e.message}")
            }
        } else {
            Log.w("VideoPlayer", "Empty video URI")
        }
    }

    LaunchedEffect(isPlaying) {
        Log.d("VideoPlayer", "isPlaying changed to: $isPlaying")
        if (isPlaying) {
            exoPlayer.play()
        } else {
            exoPlayer.pause()
        }
    }

    LaunchedEffect(isMuted) {
        exoPlayer.volume = if (isMuted) 0f else 1f
    }

    AndroidView(
        modifier = modifier.fillMaxSize(),
        factory = { context ->
            Log.d("VideoPlayer", "Creating PlayerView")
            PlayerView(context).apply {
                player = exoPlayer
                useController = false // Hide controls for screensaver
                setShowBuffering(PlayerView.SHOW_BUFFERING_NEVER)
            }
        },
    )

    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }
}

private fun createExoPlayer(
    context: Context,
    videoCacheManager: VideoCacheManager?,
    onError: (String) -> Unit,
    onBufferingChanged: (Boolean) -> Unit,
): ExoPlayer {
    Log.d("VideoPlayer", "Creating ExoPlayer, cache enabled: ${videoCacheManager != null}")
    return ExoPlayer.Builder(context)
        .apply {
            if (videoCacheManager != null) {
                Log.d("VideoPlayer", "Setting up cache with MediaSourceFactory")
                setMediaSourceFactory(videoCacheManager.buildMediaSourceFactory(context))
            }
        }
        .build()
        .apply {
            repeatMode = Player.REPEAT_MODE_ALL
            playWhenReady = true
            Log.d("VideoPlayer", "ExoPlayer created, repeatMode: $repeatMode, playWhenReady: $playWhenReady")

            addListener(
                object : Player.Listener {
                    override fun onPlayerError(error: androidx.media3.common.PlaybackException) {
                        super.onPlayerError(error)
                        Log.e("VideoPlayer", "Player error: ${error.message}", error)
                        onError("Playback error: ${error.message}")
                    }
                    
                    override fun onPlaybackStateChanged(playbackState: Int) {
                        super.onPlaybackStateChanged(playbackState)
                        val state = when (playbackState) {
                            Player.STATE_IDLE -> "IDLE"
                            Player.STATE_BUFFERING -> "BUFFERING"
                            Player.STATE_READY -> "READY"
                            Player.STATE_ENDED -> "ENDED"
                            else -> "UNKNOWN"
                        }
                        Log.d("VideoPlayer", "Playback state changed: $state")
                        
                        // Update buffering state
                        onBufferingChanged(playbackState == Player.STATE_BUFFERING)
                    }
                    
                    override fun onIsLoadingChanged(isLoading: Boolean) {
                        super.onIsLoadingChanged(isLoading)
                        Log.d("VideoPlayer", "Loading state changed: $isLoading")
                        if (isLoading) {
                            // Notify buffering started
                        }
                    }
                },
            )
        }
}
