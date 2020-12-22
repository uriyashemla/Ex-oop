package api;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.Map;

public class graphJD implements JsonDeserializer<directed_weighted_graph> {

    @Override
        public DWGraph_DS deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            directed_weighted_graph g = new DWGraph_DS();
            JsonElement CHeck = jsonObject.get("nodes");
            if (CHeck != null) {
                JsonObject nodesJson = CHeck.getAsJsonObject();
                for (Map.Entry<String, JsonElement> tmpNode : nodesJson.entrySet()) {
                    JsonElement jasonValueElement = tmpNode.getValue();
                    int Key = jasonValueElement.getAsJsonObject().get("key").getAsInt();
                    int Tag = jasonValueElement.getAsJsonObject().get("tag").getAsInt();
                    double Weight = jasonValueElement.getAsJsonObject().get("weight").getAsDouble();
                    String Info = jasonValueElement.getAsJsonObject().get("info").getAsString();

                    node_data n = new NodeData( Key,  Weight,Info, Tag);
                    g.addNode(n);
                }
                JsonObject edgesJson = jsonObject.get("edges").getAsJsonObject();
                for (Map.Entry<String, JsonElement> tmpEdge : edgesJson.entrySet()) {
                    JsonElement EdgeValue = tmpEdge.getValue();
                    for (Map.Entry<String, JsonElement> tmpK : EdgeValue.getAsJsonObject().entrySet()) {
                        JsonElement tmpEd = tmpK.getValue();
                        int EdSrc = tmpEd.getAsJsonObject().get("src").getAsInt();
                        int EdDest = tmpEd.getAsJsonObject().get("dest").getAsInt();
                        double EdWeight = tmpEd.getAsJsonObject().get("weight").getAsDouble();
                        g.connect(EdSrc, EdDest, EdWeight);
                    }
                }
                return (DWGraph_DS) g;
            }
             else {

                JsonElement GraphNodes = jsonObject.get("Nodes");
                JsonArray nodesJson = GraphNodes.getAsJsonArray();
                for (JsonElement tmpNode : nodesJson) {
                    JsonElement jasonValueElement = tmpNode.getAsJsonObject();
                    int NodeKey = jasonValueElement.getAsJsonObject().get("id").getAsInt();
                    String NodePos = jasonValueElement.getAsJsonObject().get("pos").getAsString();
                    String[] PosArr = NodePos.split(",");
                    double[] DoublePos = new double[3];
                    for (int i = 0; i < PosArr.length; i++) {
                        DoublePos[i] = Double.parseDouble(PosArr[i]);
                    }
                    node_data n = new NodeData(DoublePos[0], DoublePos[1], DoublePos[2], NodeKey);
                    g.addNode(n);
                }
                JsonArray edgesJson = jsonObject.get("Edges").getAsJsonArray();
                for (JsonElement tmpEdge : edgesJson) {
                    JsonElement tmpEd = tmpEdge.getAsJsonObject();
                    int EdSrc = tmpEd.getAsJsonObject().get("src").getAsInt();
                    int EdDest = tmpEd.getAsJsonObject().get("dest").getAsInt();
                    double EdWeight = tmpEd.getAsJsonObject().get("w").getAsDouble();
                    g.connect(EdSrc, EdDest, EdWeight);
                }
            }
            return (DWGraph_DS) g;
        }
    }



