# Passable Foliage Forked

Versão do mod: **26.2.0**  
Minecraft Java: **26.2**

## Propósito

Passable Foliage permite que jogadores e entidades atravessem folhas e outros blocos adicionados à tag `passablefoliage:passables`. O mod preserva a interação física com a folhagem por meio de redução configurável de velocidade, sons, redução de dano de queda, suporte a pathfinding e o encantamento Leaf Walker.

O projeto é um fork do [Passable Foliage](https://github.com/Snownee/PassableFoliage), criado originalmente por Snownee e distribuído sob a licença MIT.

## Compatibilidade

| Componente | Versão |
| --- | --- |
| Minecraft Java | 26.2 estável |
| Java | 25 ou superior |
| Fabric Loader | 0.19.3 ou superior |
| Fabric API | 0.158.0 ou superior para Minecraft 26.2 |
| Quilt Loader | Suportado pela camada de compatibilidade com mods Fabric |
| Ambiente | Cliente e servidor |

O mesmo JAR é usado no Fabric e no Quilt. O Fabric API continua obrigatório nos dois casos.

NeoForge não faz parte desta árvore de código: o arquivo original recebido corresponde ao ramo Fabric/Quilt.

## Principais recursos

- Remove a colisão das folhas e de blocos adicionados à tag do mod.
- Permite restringir a passagem somente a jogadores.
- Ajusta separadamente a velocidade horizontal e vertical dentro da folhagem.
- Reduz e limita dano de queda ao aterrissar em folhas.
- Reproduz sons de impacto e deslocamento com volume configurável.
- Ajusta o pathfinding para que entidades reconheçam a folhagem como passagem livre.
- Oferece o encantamento Leaf Walker para caminhar sobre folhas.
- Inclui o modo `headHitter`, que mantém colisão ao tentar atravessar folhas por baixo.
- Oculta a etiqueta de nome somente quando a entidade está totalmente dentro da folhagem.

## Mudanças da versão 26.2.0

- Atualização do Minecraft 26.1.2 para o Minecraft Java 26.2 estável.
- Atualização para Fabric Loader 0.19.3, Fabric API 0.158.0, Loom 1.17.19, Gradle 9.7.0 e Java 25.
- Remoção da dependência Kiwi, que ainda não possui versão compatível com o Minecraft 26.2.
- Registro do encantamento e das condições de recursos diretamente pelas APIs Fabric e vanilla.
- Nova configuração própria em `config/passablefoliage.json`, sem biblioteca externa.
- Migração automática das opções conhecidas do antigo `config/passablefoliage-common.yaml`; o arquivo antigo não é apagado.
- Validação de tipo, faixa numérica e tamanho máximo do arquivo de configuração.
- Escrita atômica da configuração para reduzir risco de arquivo parcialmente gravado.
- Correção de um erro que invertia a regra de ocultação de etiquetas de nome.
- Correção do modo `alwaysLeafWalking`, que não aplica mais a desaceleração da folhagem.
- Uma falha ao criar a configuração agora mantém os valores seguros já carregados em memória.
- Proteção com `try/finally` nas supressões temporárias de colisão, evitando que uma exceção deixe o estado da thread corrompido.
- Remoção de log em nível `INFO` executado no caminho de movimento de entidades a cada tick.
- Remoção de acesso interno desnecessário do Minecraft e do arquivo Access Widener.
- Atualização dos formatos de recursos e das APIs de geração de tags para o Minecraft 26.2.
- Dependências e plugins de build fixados em versões exatas; não há mais versão dinâmica `2.+`.
- Build reproduzível, checksum da distribuição Gradle e wrapper atualizado.
- Remoção do `Stripper.exe`, do script auxiliar Python, dos metadados `.git` e de arquivos de edição que não eram necessários para compilar o mod.

## Configuração

O arquivo `config/passablefoliage.json` é criado no primeiro início do jogo.

| Opção | Padrão | Faixa ou função |
| --- | ---: | --- |
| `fallDamageMultiplier` | `0.5` | 0 a 1 |
| `fallDamageThreshold` | `20` | 5 a 255 blocos |
| `speedMultiplierHorizontal` | `0.9` | 0 a 1 |
| `speedMultiplierVertical` | `0.9` | 0 a 1 |
| `modifyPathFinding` | `true` | Altera o pathfinding das entidades |
| `playerOnly` | `false` | Limita a passagem a jogadores |
| `alwaysNotViewBlocking` | `true` | Impede a folhagem de bloquear a visão |
| `alwaysLeafWalking` | `false` | Aplica permanentemente o efeito Leaf Walker |
| `headHitter` | `false` | Mantém colisão ao atravessar por baixo |
| `sounds.playerOnly` | `false` | Limita sons a jogadores |
| `sounds.volume` | `1.0` | 0 a 10 |
| `leafWalkerEnabled` | `true` | Ativa o encantamento e sua receita |

Valores fora das faixas são normalizados. Um arquivo inválido não é sobrescrito: o mod registra o erro e usa padrões seguros naquela execução.

## Compilação

As instruções completas para Windows 10 e VS Code estão em [BUILDING_WINDOWS.md](BUILDING_WINDOWS.md).

Resumo no PowerShell, dentro da pasta do projeto:

```powershell
java -version
.\gradlew.bat clean build
```

O JAR principal será criado em:

```text
build\libs\PassableFoliage-mc26.2-Fabric-26.2.0.jar
```

Para iniciar ambientes de desenvolvimento:

```powershell
.\gradlew.bat runClient
.\gradlew.bat runServer
.\gradlew.bat runDatagen
```

## Licença

MIT. Consulte [LICENSE](LICENSE).
