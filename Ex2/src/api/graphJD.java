package api;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.Map;

public class graphJD implements JsonDeserializer<directed_weighted_graph> {

    @Override
        public DWGraph_DS deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            directed_weighted_graph g = new DWGraph_DS();
            int flagi = 0;
            boolean flag = true;
            JsonElement check = jsonObject.get("nodes");
            if (check != null) {
                JsonObject nodesJson = check.getAsJsonObject();
                for (Map.Entry<String, JsonElement> temp : nodesJson.entrySet()) {
                    JsonElement values = temp.getValue();
                    int Key = values.getAsJsonObject().get("key").getAsInt();
                    int Tag = values.getAsJsonObject().get("tag").getAsInt();
                    double Weight = values.getAsJsonObject().get("weight").getAsDouble();
                    String Info = values.getAsJsonObject().get("info").getAsString();
                                       node_data nodi = new NodeData( Key,  Weight,Info, Tag);
                    g.addNode(nodi);
                    if (g.nodeSize()!=flagi+1){
                        flag=false;
                    }
                    flagi++;
                }
                flagi=0;
                JsonObject edges = jsonObject.get("edges").getAsJsonObject();
                int edgi=0;
                for (Map.Entry<String, JsonElement> tmpEdge : edges.entrySet()) {
                    JsonElement edgevlue = tmpEdge.getValue();
                    for (Map.Entry<String, JsonElement> tmpK : edgevlue.getAsJsonObject().entrySet()) {
                        JsonElement tempedge = tmpK.getValue();
                        int thesrc = tempedge.getAsJsonObject().get("src").getAsInt();
                        int thedest = tempedge.getAsJsonObject().get("dest").getAsInt();
                        double thew = tempedge.getAsJsonObject().get("weight").getAsDouble();
                        g.connect(thesrc, thedest, thew);
                        if(g.edgeSize()!=edgi+1){
                            flag=false;
                        }
                        edgi++;
                    }
                }
                return (DWGraph_DS) g;
            }
             else {

                JsonElement thenodes = jsonObject.get("Nodes");
                JsonArray Jsonnodes = thenodes.getAsJsonArray();
                for (JsonElement tempe : Jsonnodes) {
                    JsonElement valuese = tempe.getAsJsonObject();
                    int NodeKey = valuese.getAsJsonObject().get("id").getAsInt();
                    String NodePos = valuese.getAsJsonObject().get("pos").getAsString();
                    String[] PosArr = NodePos.split(",");
                    double[] DoublePos = new double[3];
                    for (int i = 0; i < PosArr.length; i++) {
                        DoublePos[i] = Double.parseDouble(PosArr[i]);
                    }
                    node_data n = new NodeData(DoublePos[0], DoublePos[1], DoublePos[2], NodeKey);
                    g.addNode(n);
                    if(g.nodeSize()!=flagi){
                        flag=false;
                    }
                    flagi++;
                }
                int edgesize = 0;
                JsonArray edgesJson = jsonObject.get("Edges").getAsJsonArray();
                flagi=0;
                for (JsonElement tmpEdge : edgesJson) {
                    JsonElement tmpEd = tmpEdge.getAsJsonObject();
                    int EdSrc = tmpEd.getAsJsonObject().get("src").getAsInt();
                    int EdDest = tmpEd.getAsJsonObject().get("dest").getAsInt();
                    double EdWeight = tmpEd.getAsJsonObject().get("w").getAsDouble();
                    g.connect(EdSrc, EdDest, EdWeight);
                    if(g.edgeSize()!=edgesize+1){
                        flag=false;
                    }
                    edgesize++;
                }
            }
            return (DWGraph_DS) g;
        }
    }



