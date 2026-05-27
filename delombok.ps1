$entity_dir = "src\main\java\com\app\pis\entity"
$files = Get-ChildItem -Path $entity_dir -Filter "*.java"

$lombok_imports = @(
    "import lombok.*;",
    "import lombok.Data;",
    "import lombok.Getter;",
    "import lombok.Setter;",
    "import lombok.AllArgsConstructor;",
    "import lombok.NoArgsConstructor;",
    "import lombok.Builder;"
)

$lombok_annotations = @(
    "@Data", "@Getter", "@Setter", "@AllArgsConstructor", "@NoArgsConstructor", "@Builder"
)

foreach ($file in $files) {
    $content = Get-Content $file.FullName -Raw
    $lines = $content -split "`r?`n"
    
    $new_lines = @()
    $fields = @()
    $class_name = $file.BaseName
    
    foreach ($line in $lines) {
        $stripped = $line.Trim()
        
        $skip = $false
        foreach ($imp in $lombok_imports) {
            if ($stripped.StartsWith($imp)) { $skip = $true; break }
        }
        foreach ($ann in $lombok_annotations) {
            if ($stripped -eq $ann) { $skip = $true; break }
        }
        if ($stripped.StartsWith("public $class_name(")) { $skip = $true }
        if ($stripped.StartsWith("public void set") -or ($stripped.StartsWith("public ") -and $stripped.Contains(" get"))) { $skip = $true }
        if ($stripped -eq "}") { $skip = $true }
        
        if (-not $skip) {
            $new_lines += $line
        }
        
        if ($line -match '^\s*private\s+([A-Za-z0-9<>, _]+)\s+([a-zA-Z0-9_]+)\s*;') {
            $fields += @{ Type = $matches[1].Trim(); Name = $matches[2].Trim() }
        }
    }
    
    $methods = @()
    $methods += "    public $class_name() {}"
    
    if ($fields.Count -gt 0) {
        $args = ($fields | ForEach-Object { "$($_.Type) $($_.Name)" }) -join ", "
        $methods += "    public $class_name($args) {"
        foreach ($field in $fields) {
            $methods += "        this.$($field.Name) = $($field.Name);"
        }
        $methods += "    }"
    }
    
    foreach ($field in $fields) {
        $cap_name = $field.Name.Substring(0,1).ToUpper() + $field.Name.Substring(1)
        $methods += "    public $($field.Type) get$cap_name() { return this.$($field.Name); }"
        $methods += "    public void set$cap_name($($field.Type) $($field.Name)) { this.$($field.Name) = $($field.Name); }"
    }
    
    $new_lines += $methods
    $new_lines += "}"
    
    $final_content = $new_lines -join "`n"
    $utf8NoBom = New-Object System.Text.UTF8Encoding $false
    [System.IO.File]::WriteAllText($file.FullName, $final_content, $utf8NoBom)
}

Write-Host "Generated getters, setters, and constructors for all entities."
