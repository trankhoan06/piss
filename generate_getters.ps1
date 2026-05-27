$files = @(
    "src\main\java\com\app\pis\entity\Inventory.java",
    "src\main\java\com\app\pis\entity\ImportReceipt.java",
    "src\main\java\com\app\pis\entity\ImportReceiptDetail.java",
    "src\main\java\com\app\pis\entity\Invoice.java"
)

foreach ($file in $files) {
    $content = Get-Content $file
    $fields = $content | Select-String -Pattern "^\s*private\s+([A-Za-z0-9<>_]+)\s+([a-zA-Z0-9_]+)\s*;"
    
    $methods = ""
    foreach ($match in $fields) {
        $type = $match.Matches[0].Groups[1].Value
        $name = $match.Matches[0].Groups[2].Value
        $capitalized = (Get-Culture).TextInfo.ToTitleCase($name)
        
        $methods += "`n    public $type get$capitalized() { return this.$name; }"
        $methods += "`n    public void set$capitalized($type $name) { this.$name = $name; }"
    }
    
    # Remove last brace
    $newContent = ($content -join "`n") -replace "\}\s*$", ""
    $newContent += $methods + "`n}`n"
    
    Set-Content -Path $file -Value $newContent -Encoding UTF8
}
