# GIT 101s

## Contenido
- [Configurar GIT](#configurar-git)
- [Clonar un proyecto](#clonar-un-proyecto)
- [GITHUB](#github)
- [Áreas de GIT](#Áreas-de-git)
- [Crear proyecto y versiones](#crear-proyecto-y-versiones)
- [Proyectos Remotos](#proyectos-remotos)
- [Modificar último commit](#modificar-último-commit)
- [Ver historial de versiones](#ver-historial-de-versiones)
- [Master y HEAD](#master-y-head)
- [Resetear versiones](#resetear-versiones)
- [Estado de los ficheros](#estado-de-los-ficheros)
- [Borrar archivos](#borrar-archivos)
- [Renombrar ficheros](#renombrar-ficheros)
- [Ánalisis del estado del área de trabajo:](#Ánalisis-del-estado-del-área-de-trabajo)
- [Ramas](#ramas)
- [Repositorios remotos](#repositorios-remotos)
- [Ramas Remotas](#ramas-remotas)
- [SSH](#ssh)
- [Operaciones sobre commit anteriores](#operaciones-sobre-commit-anteriores)
- [Borrar archivos eliminados del index de git](#borrar-archivos-eliminados-del-index-de-git)


## Configurar GIT
```bash
git config

# Para todos los proyectos en el sistema. Se guarda en /etc/gitconfig
git config --system
# Para todos los proyectos del usuario. Se guarda en ~/.gitconfig
git config --global
# Sólo para el proyecto actual. Se guarda en .git/config.
git config.

# ejemplo
git config --global user.name "Pablo Agan"
git config --global user.email pabagan@gmail.com

# Consultar opciones configuradas:
git config --list

# Consultar opción concreta:
git config user.name
```



### Ayuda en línea de comandos:
```bash
git help          # Muestra lista con los comandos existentes
git help config   # Consultar opciones existentes:
git help comando  # Ayuda sobre comando especificado
git help add      # Ayuda sobre el comando add
git add --help    # Equivalente a anterior
man git-add       # Equivalente a anterior
```

### Manual de referencia, chuletas, videos, otros enlaces:
* http://ndpsoftware.com/git-cheatsheet.html
* https://na1.salesforce.com/help/doc/en/salesforce_git_developer_cheatsheet.pdf


## Clonar un proyecto

La copia incluye el proyecto con toda su historia de versiones

```bash
# sobre directorio donde se clonará
git clone <URL_repositorio>
# indicando nombre del directorio
git clone https://github.com/jquemada/quiz-2015 /etc/user/mi_proyecto


# Muestra las versiones del proyecto
git log --oneline
# descongela las versiones de la historia
git checkout <commit_id_SHA1>
# vuelve a la rama master
git checkout master
```

## GITHUB

### Fork
Fork permite copiar un repositorio (proyecto) en nuestra cuenta en GITHUB
para acceder a él y evolucionarlo.


### Contribuir
1. "Fork" en la cuenta propia.
2. Clonar la rama creada en nuestra cuenta en nuestro ordenador local.
3. Modificar proyecto local -> subir -> GITHUB.
4. Hacer “Pull Request” desde GITHUB pidiendo al administrador que
introduzca nuestros cambios.
```bash
git clone https://github.com/pepe/proy1
git add .
git commit -m "Mi nueva piliculi"
git push origin master
```


## Áreas de GIT

### 1. Área de cambios o índice (staging area, index)
Ficheros indexados para la próxima versión: borrados, nuevos o modificados.
```bash
git add # añade al índice
git commit # crea versión
```

    (*) OJO! Un fichero modificado pero no indexado no se incluirá
    en la versión.

### 2. Carpeta .git
Contiene todas las versiones del proyecto.
git checkout # reconstruye versiones del proyecto en directorio de trabajo.

### 3. documento en root .gitignore
gitignore es un fichero que informa de los ficheros que no debe gestionar GIT.
* Puede usarse los comodinesy ?.
* Patrones terminados en / indican directorios.
* Un patron que empiece con ! indica negación.
* Se ignoran líneas en blanco y que comiencen con:
    * [abc] seleccion de caracteres
    * [a-z] rengo


```bash
# Ejemplo
private.txt     # excluir los ficheros con nombre "private.txt"
*.class         # excluir los ficheros acabados en “.class”
*.[oa]          # excluir ficheros acabados en “.o” y “.a”
!lib.a          # no excluir el fichero "lib.a"
*~              # excluir ficheros acabados en “~”
testing/        # excluir directorio "testing"
# Ejemplo
# Node.js
lib-cov
*.seed
*.log
*.csv
*.dat
*.out
*.pid
*.gz
pids
logs
results
npm-debug.log
node_modules

# Bower
src/components

# Sass
.sass-cache

# OS X
.DS_Store
.AppleDouble
.LSOverride
Icon
._*
.Spotlight-V100
.Trashes

# Windows
Thumbs.db
ehthumbs.db
Desktop.ini
$RECYCLE.BIN/
```



## Crear proyecto y versiones

```bash
git init                          # Se inicia proyecto, creando repositorio vacío en .git/
git add random.js                 # añade fichero random.js al índice
...........                       # Crear .gitignore con ficheros no indexados
git add .                         # añade resto de cambios a índice git add file1 file2 ...
git commit -m 'random versión 1'  # congela 1a versión
...........                       # se modifica random.js
git add .                         # añade cambios a índice
git commit -m 'random versión 2' # congela 2a versión


## Proyectos Remotos

# asociar dirección de remoto a origin
git remote add origin https://github.com/jquemada/random
# subimos la rama “master” a repositorio remoto “origin”
git push origin master
# clonar
git clone https://github.com/jquemada/random random-2
# copiar repositorio
cp -r random random-3
```


### Ayudas

```bash
# muestra ayuda en línea (manual) de “git init”
git init --help
# muestra ayuda en línea (manual) de “git add”
git add --help
# muestra ayuda en línea (manual) de “git commit”
git commit --help
```




## Modificar último commit
```bash
git commit --amend -m ...
git commit -m 'buscador_de_preguntas'
git add .
# Repetimos git commit con opción --amend y un mensaje de log (modificado o no
git commit --amend -m "buscador_de_preguntas"
# Se actualiza el commit erróneo con los nuevos cambios introducidos
```

(*) no realizar --amend sobre un commit que se haya hecho público -> Sentido común, otros pueden haberlo usado/modificado


## Ver historial de versiones
```bash
git log --stat            # muestra estadísticas
git log —graph            # muestra árbol
git log --since=2.weeks  # muestra commits últimas 2 semanas
git log --oneline        # muestra resumen de cada commits en 1 linea
git log -5               # muestra 5 últimos commits
git log -p -1             # La historia de diferencias entre commits se muestra con opción -p, por ejemplo “git log -p -1”
```


## Master y HEAD

### Master:
Rama principal del desarrollo. Git init inicia el proyecto en la rama master.
Las versiones se crean en master (salvo que se pase a otra rama).

### HEAD
Versión (commit) actual del directorio de trabajo. Cada "git commit ..." crea una nueva versión, actualizando los punteros master y HEAD.


## Resetear versiones
```bash
# eliminar
git reset "commit_id"
# listar commits
git log --oneline

git reset 34ac2   # restaura versión 34ac2 ‘añadir ejemplo’ dejando los cambios
                    # realizados en las versiones eliminadas en directorio de trabajo
                    # sin añadir al índice (staging-area)
git reset --hard 34ac2    # restaura 34ac2 ‘añadir ejemplo’ eliminando
                            # todos los cambios de las versiones eliminada
```



## Estado de los ficheros
```bash
git status
```

* **Untracked**: Ficheros que no están bajo el control de versiones.
* **Tracked**: Ficheros registrados en versión (con git add .).
* **Modified**: Ficheros modificados, no incluidos en próximo commit con "git add".
* **Unmodified**: Ficheros no modificados, que siguen en próximo commit.
* **Staged**: Ficheros modificados, incluidos en próximo commit.
* **Ignorados:**: Ficheros indicados en .gitignore.


## Borrar archivos

El comando del S.O. rm borra ficheros del directorio de trabajo, pero no
los borra del staging area.

```bash
rm CharIO.java
# Borra el fichero del directorio de trabajo y del staging area.
git rm CharIO.java
# Borra fichero del staging area. No lo borra del directorio de trabajo.
# Tras el próximo commit dejará de estar tracked.
git rm --cached CharIO.java
# Es como hacer una modificación en el contenido del fichero.
```



## Renombrar ficheros

### Mover o renombrar un fichero:
```bash
git mv filename_old filename_new
```



## Ánalisis del estado del área de trabajo:
```bash
git diff  # git diff muestra diferencias en ficheros modificados y no indexados (staged)
git diff --cached
git diff --staged # muestra diferencias en ficheros modificados e indexados.
```

## Ramas
Rama: desarrollo que diverge de la rama master o de otra rama.

```bash
# Ver las ramas creadas:
git branch
# Crear ramas
git branch <nombre_de_rama>
# Cambiar rama:
git checkout <nombre_rama>
# aportes a la rama
git commit -m 'Siguiente version principal'
# deshace cambios staged de area de trabajo
git checkout .
# deshace cambios stagged de <fichero> en area de trabajo
git checkout -- <fichero>
```


### Unir ramas
```bash
git merge <rama>
# Ejemplo:
git checkout master   # Estamos en la rama master
git merge iss53       # incorporamos los cambios hechos
                        # en la rama iss53 en la rama master
```

#### Conflictos
Si dos ramas han modificado las mismas líneas de un fichero. No se realiza el commit y se marcan conflictos. Ej:

```bash
<div id="footer">contact us at support@github.com</div>
```

##### mostrar unmerged
```bash
git status
```

##### resolver el conflicto hay que:
```bash
git add y git commit.
```


### Borrar ramas
```bash
git branch -d <nombre>
# borrar independientemente de si ha sido merged
git branch -D <nombre>
# Listar ramas
git branch

# Opciones:
-r muestra ramas remotas
-a muestra todas las ramas (locales y remotas)
-v muestra el último commit de la rama.
--merged muestra las ramas que ya se han mezclado con la la rama actual.
--no-merged muestra las ramas que aun no se han
```


## Repositorios remotos
```bash
# listar repositorios remotos
git remote
# ver url de repositorio
git remote -v
# crear remote shortname URL
git remote add origin git://github.com/pepe/planet.git
# Inspeccionar detalles de un remote:
git remote show [nombre_del_remote]
# Renombrar un remote
git remote rename nombre_viejo nombre_nuevo
# Borrar un remote:
git remote rm nombre_del_remote
# Para actualizar la informacion sobre los remotes definidos
git remote update
# Para borrar ramas que ya no existen en el remote:
git remote prune
```

Los repositorios remotos se pueden manejar con 3 tipos de URL
* git: git@github.com:jquemada/swcm2012.git.
* ssh: ssh://github.com/jquemada/swcm2012.
* https: https://github.com/jquemada/swcm2012.

## Ramas Remotas
```bash
Se nombran como: <remote>/<rama>.
```


## SSH
```bash
# añadir directamente SSH
git remote add origin git@github.com:pabagan/gx-align
git remote add origin git@github.com:pabagan/modernizr-custom
git remote add origin git@github.com:geniuxthemes/bonicons.git

# cambiar remote url a versión SSH
git remote set-url origin git@github.com:pabagan/gx-align

# Ahora Github no pedirá login
git push -u -f origin master
```



### Tracking Branch
Ramas sincronizadas remota-local
```bash
# actualizar rama remota con rama local.
git push
# ex
git push -f origin <branch>
git push -f origin buscador_de_preguntas
# actualizar rama local con rama remota.
git pull

# Crear una tracking branch
git checkout -b <branchname> <remotename>/<branchname>
# Se crea una rama local con seguimiento a remota
git checkout --track <remotename>/<branchname>
# listar
git branch -vv
git remote show <remote_name>
```



### Descargar datos de un remote
```bash
# Bajarse los datos que aun no tengo del repositorio del que me cloné:
git fetch origin
# Ahora mezclo mi rama actual con la rama demo de origin:
git merge origin/demo
```



### Descargar datos y Mezclar
```bash
# cambios realizados en la rama asociada del repositorio remoto.
git pull origin # Actualiza rama actual con los cambios en origin.
git pull
# Por defecto se realiza un pull de origin.
# Este comando ejecuta un fetch con los argumentos dados, y despues realiza
# un merge en la rama actual con los datos descargados.
git pull pepito demo
```



### Subir datos a un remote
```bash
# Subir rama local a origin
git push [nombre_del_remote]
# Subir los cambios de la rama local actual o origin.
git push origin
# Subir cambios de rama local actual o master.
git push origin master

Sólo puede realizarse si:
 - se tiene permiso de escritura en el repositorio remoto.
 - nadie ha subido nuevos cambios al repositorio remoto, es decir,

Si en el remote hay actualizaciones que no tenemos, deberemos hacer
pull antes.

# Subir rama local uno a la rama dos del repositorio origin:
git push origin uno:dos
# Subir rama local actual a la dos del repositorio origin:
git push origin :dos
```


## Operaciones sobre commit anteriores

### Cambiar nombre a commit
```bash
git commit --amend
```

### Unir 2 commits duplicados
```bash
# Cargar editor volviendo al head
git rebase -i HEAD~7

# tenemos 2 duplicados tal que así
pick f392171 Removed most clearfixs in templates
pick ba9dd9a Removed most clearfixs in templates

# cambiamos a
pick f392171 Removed most clearfixs in templates
squash ba9dd9a Removed most clearfixs in templates # este cambia pick por squash
```

### Añadir cambio a commit pasado (no último)
```bash
git rebase -i HEAD~10 # (or whatever you need to see far enough back).
# Mark the commit in question (a0865...) for edit.
# Save the rebase file, and git will drop back to the shell and wait for you to fix that commit.
# add
git add .
# Amend
git commit --amend.
# Continue
git rebase --continue
```

### Volver a commit anterior
```bash
git reset COMMIT_ID
```

## Borrar archivos eliminados del index de git
```bash
git ls-files --deleted -z | xargs -0 git rm
```