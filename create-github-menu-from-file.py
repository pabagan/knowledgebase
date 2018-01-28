import re

# The programs takes one file with github sintax
# and build a file with a github navigation.
#
# Example:
# from: ## Rails REST Convention
# to: 	- [Rails REST Convention](#rails-rest-convention)


#
# 1. Build a list with menu items from file
#
#filename = "README.md"
#filename = "./SMACSS/README.md"
#filename = "./GIT/GIT_101s.md"
#filename = "./Sass/README.md"
# Rails
#filename = "Ruby/RubyOnRails/Active_Record.md"
#filename = "Ruby/RubyOnRails/Action_Pack.md"
#filename = "Ruby/RubyOnRails/i18n.md"
#filename = "Ruby/RubyOnRails/Rails_Basics.md"
#filename = "Ruby/RubyOnRails/Web_Services_with_MongoDB.md"
filename = "MySQL/README.md"
filename = "python/python101s.md"


fhand = open(filename)
nav_items = []

# Loop file
for line in fhand:
	# Lines with ##
	if re.search('^##\s', line) :
		line = line.replace('\n', '')
		line = line.replace('## ', '')
		#line = line.lstrip(" ")
		nav_items.append(line)

# print nav_items

#
# 2. Build menu in a new file
# filename + "_nav".
#
def build_nav_item(name):
	# Build menu item strins helper
	# from: Ruby Basics
	# to: - [Ruby Basics](#ruby-basics)
	# Replace spaces and lowercases
	link = name.replace(" ", "-").lower()

	# replace special chars
	link = re.sub("[+*:,_'()/@&]", "", link)

	return "- [" + name + "](#" + link + ")"


new_file_name = filename.replace('.md', '') + '-nav.md'

nav_heading = "Table of contents"
nav_content = ''
nav_content += "## " + nav_heading + "\n"
# Lopp in item list calling helper
# built_na_item.
for line in nav_items:
	print line
	# avoid insert self heading
	if nav_heading in line:
		continue
	# build menu item string
	nav_content += build_nav_item(line) + "\n"


#
# 3. Create & Write new file
#
filename_nav = open(new_file_name, 'w+')
filename_nav.write(nav_content)

print new_file_name , 'created!'