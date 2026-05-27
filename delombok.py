import os
import re

entity_dir = r"src\main\java\com\app\pis\entity"

lombok_imports = [
    "import lombok.*;",
    "import lombok.Data;",
    "import lombok.Getter;",
    "import lombok.Setter;",
    "import lombok.AllArgsConstructor;",
    "import lombok.NoArgsConstructor;",
    "import lombok.Builder;"
]

lombok_annotations = [
    "@Data", "@Getter", "@Setter", "@AllArgsConstructor", "@NoArgsConstructor", "@Builder"
]

def capitalize(s):
    if len(s) == 0:
        return s
    return s[0].upper() + s[1:]

for filename in os.listdir(entity_dir):
    if filename.endswith(".java"):
        filepath = os.path.join(entity_dir, filename)
        with open(filepath, "r", encoding="utf-8") as f:
            lines = f.readlines()
        
        new_lines = []
        fields = []
        class_name = filename.replace(".java", "")
        
        inside_class = False
        
        for line in lines:
            stripped = line.strip()
            # Remove lombok imports
            if any(stripped.startswith(imp) for imp in lombok_imports):
                continue
            # Remove lombok annotations
            if stripped in lombok_annotations:
                continue
            # Remove existing getters and setters and constructors
            if stripped.startswith("public " + class_name + "("):
                continue
            if stripped.startswith("public void set") or stripped.startswith("public ") and " get" in stripped and "{" in stripped:
                continue
            if stripped == "}" and len(new_lines) > 0 and new_lines[-1].strip() == "":
                pass # will handle at end
                
            # also skip empty lines that might be leftover from removing methods
            if stripped == "}" or stripped == "":
                if not re.match(r'^\s*private\s+', line):
                    pass # We'll filter out the closing brace and re-add it

            if stripped != "}" and not stripped.startswith("public " + class_name + "(") and not stripped.startswith("public void set") and not (stripped.startswith("public ") and " get" in stripped):
                new_lines.append(line)
            
            # Find fields to generate getters and setters
            match = re.match(r'^\s*private\s+([A-Za-z0-9<>, _]+)\s+([a-zA-Z0-9_]+)\s*;', line)
            if match:
                type_name = match.group(1).strip()
                field_name = match.group(2).strip()
                fields.append((type_name, field_name))

        # Generate getters and setters
        methods = []
        
        # We also need empty constructor and all args constructor because we removed @NoArgsConstructor and @AllArgsConstructor
        methods.append(f"    public {class_name}() {{}}\n")
        
        if fields:
            args = ", ".join([f"{t} {n}" for t, n in fields])
            methods.append(f"    public {class_name}({args}) {{\n")
            for _, n in fields:
                methods.append(f"        this.{n} = {n};\n")
            methods.append(f"    }}\n")

        for type_name, field_name in fields:
            cap_name = capitalize(field_name)
            methods.append(f"    public {type_name} get{cap_name}() {{ return this.{field_name}; }}\n")
            methods.append(f"    public void set{cap_name}({type_name} {field_name}) {{ this.{field_name} = {field_name}; }}\n")

        new_lines.extend(methods)
        new_lines.append("}\n")

        with open(filepath, "w", encoding="utf-8") as f:
            f.writelines(new_lines)

print("Generated getters, setters, and constructors for all entities.")
