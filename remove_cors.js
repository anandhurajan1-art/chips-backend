const fs = require('fs');
const path = require('path');
const dir = 'src/main/java/com/chips/sales_system/controller';

fs.readdirSync(dir).forEach(file => {
    if (file.endsWith('.java')) {
        const fullPath = path.join(dir, file);
        let content = fs.readFileSync(fullPath, 'utf8');
        content = content.replace(/@CrossOrigin\(origins = "\*", maxAge = 3600\)\r?\n?/g, '');
        fs.writeFileSync(fullPath, content);
    }
});
console.log('Removed @CrossOrigin');
