 # Coold commands

```bash
# take one line
sudo apt-cache search dom | grep php
# search folder or files 20160303
cat /etc/php/7.1/cli/**/* | grep 20160303
# inside x file return the line
cat php.ini | grep dll
# return lines containing extension
grep extension /etc/php/7.1/cli/php.ini
```