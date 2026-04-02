# JustReports

A lightweight **Paper/Spigot** plugin for player ↔ staff communication: players submit a **question** via `/report`, staff open `/reports` to pick it up, and both sides continue in a guided in-game dialog until it’s closed.

## Table of Contents

- [Features](#features)
- [Supported Platforms](#supported-platforms)
- [Installation](#installation)
- [Usage](#usage)
  - [Player Commands](#player-commands)
  - [Staff Commands](#staff-commands)
  - [Typical Q&A Flow](#typical-qa-flow)
- [Configuration](#configuration)
  - [`config.yml` (database)](#configyml-database)
  - [`messages.yml`](#messagesyml)
  - [Menu configs](#menu-configs)
- [Permissions](#permissions)
- [Building From Source](#building-from-source)
- [Contributing](#contributing)
- [License](#license)

## Features

- Players ask staff a question: `/report <question>`
- Staff view and pick up incoming questions in a GUI: `/reports`
- “Take/assign” logic so the same question isn’t answered by multiple staff members
- Staff ↔ player dialog messaging with configurable chat formats
- Simple reputation command: `/reputation [player]`
- Reload config files without restart: `/justreports reload`
- Config files are generated on first start

## Supported Platforms

- Server software: **Paper** (should also work on **Spigot**, but Paper is recommended)
- Minecraft API version: **1.13+** (see `api-version` in `plugin.yml`)
- Java: built for **Java 8**

## Installation

1. Download/build the plugin JAR.
2. Put it into your server’s `plugins/` folder.
3. Start the server.

On first start, the plugin will generate:

- `plugins/JustReports/config.yml`
- `plugins/JustReports/messages.yml`
- `plugins/JustReports/menu/report_list.yml`
- `plugins/JustReports/menu/feedback_rating.yml`

## Usage

### Player Commands

- `/report <question>` — sends your question to online staff.

### Staff Commands

- `/reports` — opens the question/report list menu.
- `/reputation` — shows your reputation.
- `/reputation <player>` — shows another player’s reputation.
- `/justreports reload` — reloads config files.

### Typical Q&A Flow

1. A player uses `/report <question>`.
2. Online admins receive a “new question” notification.
3. A staff member opens `/reports`, selects the question, and takes it.
4. A dialog starts: the player and staff member exchange messages.
5. The staff member closes the dialog when the question is resolved.
6. Optionally, the player can leave a star rating (reputation).

## Configuration

All configuration files use YAML.

### `config.yml` (database)

JustReports stores reputation data in a database. By default, it uses SQLite.

Key settings (see `MainConfig.DatabaseConfig`):

- `database.type`: `SQLITE` or `MYSQL`
- `database.path`: path to the SQLite file (used for `SQLITE`)
- `database.host`, `database.port`, `database.database`, `database.username`, `database.password`: used for `MYSQL`

### `messages.yml`

All user-facing messages are configurable, including:

- player messages (e.g. “question sent”, “already sent a question”)
- admin messages (e.g. “new question from …”)
- dialog chat formats
- command usage strings

Color codes use the standard Minecraft `§` format.

### Menu configs

The plugin uses menu configuration files under `plugins/JustReports/menu/`:

- `report_list.yml` — how the question list GUI looks
- `feedback_rating.yml` — how the feedback/reputation GUI looks

## Permissions

Defined in `plugin.yml`:

| Permission | Default | Description |
|---|---:|---|
| `justreports.report` | true | Allows asking a question via `/report` |
| `justreports.reports` | op | Allows opening the list GUI via `/reports` |
| `justreports.reports.answer` | op | Allows answering/taking questions (used internally by staff actions) |
| `justreports.see` | op | Allows seeing incoming questions (used by staff UI) |
| `justreports.reputation` | op | Allows `/reputation` |
| `justreports.reputation.other` | op | Allows `/reputation <player>` |
| `justreports.reload` | op | Allows `/justreports reload` |
| `justreports.*` | false | Grants all JustReports permissions |

## Building From Source

Requirements:

- JDK 8+ (the project targets Java 8 bytecode)
- Gradle Wrapper (included)

Build a shaded JAR:

```bash
./gradlew shadowJar
```

The output JAR will be placed under `build/libs/`.

## Contributing

Contributions are welcome.

1. Fork the repo
2. Create your branch: `git checkout -b feat/my-change`
3. Commit your changes: `git commit -m "feat: ..."`
4. Push the branch: `git push origin feat/my-change`
5. Open a Pull Request

If you’re changing commands/messages/config, please keep backward compatibility when possible and update this README.

## License

See [LICENSE](LICENSE).
