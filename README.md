# Calendar Widget

An Android home screen widget that displays calendar events from Google Calendar and iCal sources.

## Features

- **Home Screen Widget**: Displays upcoming events in a clean, scrollable list
- **Google Calendar Integration**: Sync multiple Google Calendars with color coding
- **iCal Support**: Subscribe to any iCal URL (e.g., holiday calendars, work schedules)
- **Background Sync**: Automatic updates via WorkManager
- **Material Design**: Modern UI with day grouping and color-coded events

## Screenshots

<table>
  <tr>
    <td><img src="assets/widget_preview.png" alt="Widget" width="200"/></td>
    <td><img src="assets/app_main.png" alt="Main App" width="200"/></td>
    <td><img src="assets/settings.png" alt="Settings" width="200"/></td>
  </tr>
</table>

## Requirements

- Android 8.0+ (API 26)
- Google account (for Google Calendar sync)
- Internet connection for syncing

## Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/tiagovicente2/CalendarWidget.git
   ```

2. Open in Android Studio

3. Add your Google OAuth client ID to `res/values/strings.xml`:
   ```xml
   <string name="default_web_client_id">YOUR_CLIENT_ID</string>
   ```

4. Build and run on an Android device or emulator

## Architecture

- **MVVM pattern** with Repository layer
- **Room Database** for local event storage
- **Hilt** for dependency injection
- **Kotlin Coroutines** for async operations
- **Retrofit + OkHttp** for network requests
- **Google Calendar API** for Google sync
- **iCal4j** for iCal parsing

## Project Structure

```
app/src/main/java/com/calendar/widget/
├── data/          # Database, models, and repository
├── di/            # Dependency injection modules
├── parser/        # iCal parsing logic
├── service/       # Background sync worker
├── sync/          # Google Calendar and iCal sync
├── ui/            # Activities and adapters
├── util/          # Logger and utilities
└── widget/        # Home screen widget implementation
```

## Permissions

- `READ_CALENDAR` - Read calendar events
- `INTERNET` - Sync with Google Calendar and iCal feeds
- `ACCESS_NETWORK_STATE` - Check connectivity for sync
- `RECEIVE_BOOT_COMPLETED` - Restore widget after reboot

## Contributing

1. Fork the repository
2. Create a feature branch: `git checkout -b feature/my-feature`
3. Commit your changes: `git commit -am 'Add new feature'`
4. Push to the branch: `git push origin feature/my-feature`
5. Open a pull request

## License

MIT License. See LICENSE file for details.
