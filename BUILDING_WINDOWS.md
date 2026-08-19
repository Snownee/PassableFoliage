# Compilar no Windows 10 com VS Code

## 1. Instalar o Java correto

O Minecraft Java 26.2 e este projeto exigem **Java 25**.

1. Baixe o instalador Windows x64 do [Microsoft Build of OpenJDK 25](https://learn.microsoft.com/java/openjdk/download).
2. Execute o instalador MSI.
3. Marque as opções para configurar `JAVA_HOME` e adicionar o Java ao `PATH`, caso sejam apresentadas.
4. Feche e abra novamente o PowerShell e o VS Code.

Valide:

```powershell
java -version
javac -version
```

Os dois comandos devem informar a versão 25. Se outra versão aparecer, ajuste temporariamente a sessão antes de compilar:

```powershell
$env:JAVA_HOME = "C:\Program Files\Microsoft\jdk-25.0.x"
$env:Path = "$env:JAVA_HOME\bin;$env:Path"
```

Substitua `jdk-25.0.x` pelo nome exato da pasta instalada.

## 2. Preparar o VS Code

Instale no VS Code:

- **Extension Pack for Java**, da Microsoft.
- **Gradle for Java**, da Microsoft.

Extraia o projeto em um caminho curto e gravável, por exemplo:

```text
C:\Projetos\PassableFoliageForked
```

Evite compilar diretamente dentro do ZIP, em `Program Files` ou em uma pasta sincronizada que bloqueie arquivos temporários.

No VS Code, escolha **File > Open Folder** e abra a pasta que contém `build.gradle` e `gradlew.bat`. Aguarde a importação do projeto Gradle terminar.

## 3. Compilar

Abra **Terminal > New Terminal** no VS Code e execute:

```powershell
.\gradlew.bat --version
.\gradlew.bat clean build
```

Na primeira execução, o wrapper baixa o Gradle 9.7.0 e as dependências oficiais. A distribuição Gradle é validada pelo checksum configurado no projeto.

Resultado esperado:

```text
BUILD SUCCESSFUL
```

Arquivos gerados:

```text
build\libs\PassableFoliage-mc26.2-Fabric-26.2.0.jar
build\libs\PassableFoliage-mc26.2-Fabric-26.2.0-sources.jar
```

O primeiro é o mod. O segundo contém apenas o código-fonte publicado pelo Gradle.

## 4. Testar no ambiente de desenvolvimento

Cliente de teste:

```powershell
.\gradlew.bat runClient
```

Servidor dedicado de teste:

```powershell
.\gradlew.bat runServer
```

Regenerar arquivos de dados:

```powershell
.\gradlew.bat runDatagen
```

Depois do `runDatagen`, revise as alterações em `src\generated\resources` antes de publicá-las.

## 5. Erros comuns

### `Unsupported class file major version` ou Java diferente de 25

O terminal ou o Gradle está usando outro JDK. Confira:

```powershell
java -version
echo $env:JAVA_HOME
.\gradlew.bat --version
```

Reabra o VS Code depois de corrigir `JAVA_HOME`.

### Download de dependências falhou

Confirme acesso HTTPS a estes serviços:

- `services.gradle.org`
- `maven.fabricmc.net`
- `repo.maven.apache.org`
- serviços de download da Mojang/Minecraft

Depois tente:

```powershell
.\gradlew.bat --refresh-dependencies clean build
```

### Arquivo em uso durante `clean`

Feche instâncias de `runClient`, encerre terminais antigos e tente novamente. Se necessário, feche e reabra o VS Code.

## 6. Instalar o resultado

Copie somente o JAR principal para a pasta `mods` da instância Minecraft 26.2. Instale também:

- Fabric Loader 0.19.3 ou superior, ou Quilt Loader compatível com 26.2.
- Fabric API 0.158.0 ou superior para Minecraft 26.2.

Cliente e servidor devem usar versões compatíveis do mod e do Fabric API.
