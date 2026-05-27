$files = @('src\main\java\com\app\pis\entity\Inventory.java', 'src\main\java\com\app\pis\entity\ImportReceipt.java', 'src\main\java\com\app\pis\entity\ImportReceiptDetail.java', 'src\main\java\com\app\pis\entity\Invoice.java')
foreach ($file in $files) {
    $content = Get-Content $file -Raw
    $utf8NoBom = New-Object System.Text.UTF8Encoding $false
    [System.IO.File]::WriteAllText((Resolve-Path $file).Path, $content, $utf8NoBom)
}
