import os
import glob

controller_dir = r"c:\Users\anand\.gemini\antigravity\scratch\chips_sales_system\backend\src\main\java\com\chips\sales_system\controller"
for filepath in glob.glob(os.path.join(controller_dir, "*.java")):
    with open(filepath, "r", encoding="utf-8") as f:
        content = f.read()
    
    content = content.replace('@CrossOrigin(origins = "*", maxAge = 3600)\n', '')
    content = content.replace('@CrossOrigin(origins = "*", maxAge = 3600)', '')
    
    with open(filepath, "w", encoding="utf-8") as f:
        f.write(content)
print("Removed @CrossOrigin from controllers.")
