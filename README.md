# zBroadcast
### A dead simple broadcasting plugin (and I mean simple)

A fast and simple broadcasting spigot plugin! No extras attached

## Config.yml

- _broadcast-tag:_ prefix for broadcasting, can be null.
- _send-to-console:_ sends a message to console
- _delay:_ delay in seconds.
- _deny-message-list-update:_ denies any editing of the list via Commands (even when Op)
- _messages:_ List of messages.

## Commands

- _/zbroadcast_ - displays current info such as is it enabled or not.
- _/zbroadcast info_ - same as top
- _/zbroadcast help_ - if you dont know the commands listed here
- _/zbroadcast list_ <pageIndex> - message list in page increments of 5
- _/zbroadcast reload_ - Reloads the plugin config (If you dont use a plugin manager)

## Permissions

- zyphinia.broadcast - provides access to /zbroadcast

## Building the plugin

Can use anything that uses maven. IntelliJ IDEA Preferred

## License

Licensed under the BSD License, check LICENSE file for more information

## Future Plans

- Modularize architecture (core system maybe?)
- Maybe expand to other types of broadcasting

## What's NOT planned

- BossBar Broadcasting
- StatusBar
