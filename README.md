AWARE Plugin: YOUR PLUGIN
==========================

This plugin measures ...

# Settings
Parameters adjustable on the dashboard and client:
- **status_plugin_template**: (boolean) activate/deactivate plugin

# Broadcasts
**ACTION_AWARE_PLUGIN_TEMPLATE**
Broadcast ..., with the following extras:
- **value_1**: (double) amount of time turned off (milliseconds)

# Providers
##  Template Data
> content://com.aware.plugin.template.provider.xxx/plugin_template

Field | Type | Description
----- | ---- | -----------
_id | INTEGER | primary key auto-incremented
timestamp | REAL | unix timestamp in milliseconds of sample
device_id | TEXT | AWARE device ID
