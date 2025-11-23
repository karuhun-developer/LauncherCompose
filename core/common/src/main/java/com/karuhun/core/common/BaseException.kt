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

package com.karuhun.core.common

open class BaseException(message: String) : Exception(message)

class BadRequestException(message: String = "") :
    BaseException(message.ifEmpty { "The request could not be understood by the server due to malformed syntax." })

class AuthorizationException(message: String = "") :
    BaseException(message.ifEmpty { "You are not authorized to access this resource." })

class NotFoundException(message: String = "") :
    BaseException(message.ifEmpty { "The requested resource could not be found." })

class UnknownException(message: String = "") :
    BaseException(message.ifEmpty { "An unknown error occurred, please try again later." })

class NetworkException(message: String = "") : BaseException(
    message.ifEmpty { "Please check your internet connection." }
)

class ApplicationNotInstalledException(message: String = "") :
    BaseException(message.ifEmpty { "The application is not installed on this device." })

class ApplicationLaunchException(message: String = "") :
    BaseException(message.ifEmpty { "Failed to launch the application." })

class ApplicationSecurityException(message: String = "") :
    BaseException(message.ifEmpty { "A security exception occurred while launching the application." })