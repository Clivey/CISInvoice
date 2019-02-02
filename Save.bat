if exist "Y:\My Backups\Android Studio Backups\cisInvoice" goto start
md "Y:\My Backups\Android Studio Backups\cisInvoice"

:start
xcopy *.* /s /y "Y:\My Backups\Android Studio Backups\cisInvoice"