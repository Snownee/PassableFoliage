def fdCutting(action): walk(
    if type == "object" and has("neoforge:type") and ."neoforge:type" == "farmersdelight:tool" and ."tag" == "c:tools/axes" then
        ."neoforge:type" = "farmersdelight:tool_action"
        | ."action" = action
        | del(."tag")
    end
);
if has("fabric:load_conditions") then
    ."neoforge:conditions" = ."fabric:load_conditions"
    | del(."fabric:load_conditions")
    | ."neoforge:conditions" |= walk(
        if type == "object" and has("condition") then
            if ."condition" == "fabric:not" then
                ."condition" = "neoforge:not"
            end
            | if ."condition" == "fabric:and" then
                ."condition" = "neoforge:and"
            end
            | if ."condition" == "fabric:or" then
                ."condition" = "neoforge:or"
            end
            | if ."condition" == "fabric:all_mods_loaded" then
                ."condition" = "neoforge:and"
                | ."values" |= [.[] | {
                    "type": "neoforge:mod_loaded",
                    "modid": .
                }]
            end
            | if ."condition" == "fabric:any_mod_loaded" then
                ."condition" = "neoforge:or"
                | ."values" |= [.[] | {
                    "type": "neoforge:mod_loaded",
                    "modid": .
                }]
            end
            | if ."condition" == "fabric:tags_populated" then
                ."condition" = "neoforge:not"
                | ."value" = {
                    "type": "neoforge:or",
                    "values": [."values".[] | {
                        "type": "neoforge:tag_empty",
                        "tag": .
                    }]
                }
                | del(."values")
            end
            | ."type" = ."condition" | del(."condition")
        end
    )
end
| walk(
    if type == "object" and has("fabric:type") then
        . #TODO: custom ingredients fix
        | ."neoforge:type" = ."fabric:type" | del(."fabric:type")
        | if ."neoforge:type" == "fabric:any" then
            . = ."ingredients"
        end
    end
)
| if has("type") and ."type" == "farmersdelight:cutting" then
    if has("sound") and ."sound" == "minecraft:item.axe.strip" then
        fdCutting("axe_strip")
    else
        fdCutting("axe_dig")
    end
end