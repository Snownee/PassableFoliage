# Security

## Scope

The mod does not implement custom network communication, process execution, native library loading or runtime content downloads.

The configuration loader only consumes documented fields and ignores additional fields. It enforces a 64 KiB size limit, uses strict JSON parsing, validates numeric ranges and writes new files through atomic replacement when supported. Invalid configurations are not overwritten automatically.

The source package contains no bundled executable other than the official Gradle Wrapper JAR. The Gradle distribution checksum is pinned in `gradle-wrapper.properties`.

## Reporting a vulnerability

Do not publish exploitable details in a public issue. Contact the fork maintainer through a private channel provided by the repository. If no private channel is available, open an issue without a proof of concept and ask for a secure contact method.
