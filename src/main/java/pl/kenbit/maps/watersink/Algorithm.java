package pl.kenbit.maps.watersink;

import static java.lang.Math.abs;

import java.util.ArrayList;
import gov.nasa.worldwind.util.Logging;


public class Algorithm {

    private Maps maps;
    private double sourceWaterHeight;
    private int lengthTab;
    private int widthTab;
    private Boolean[][] waterDirectionMap;
    private ArrayList<Double> coordinateListDouble;
    private ArrayList<Double> listOfFloodValueCopy;
    private ArrayList<Double> listOfFloodCoordinateCopy;
    private ArrayList<Double> listOfFloodValue;
    private ArrayList<Integer> coordinateListInteger;
    private Double[][] elevationsMap;
    private double maxGeoLat;
    private double minGeoLat;
    private double maxGeoLon;
    private double minGeoLon;
    private double waterSourcePointLat;
    private double waterSourcePointLon;
    private double waterPointLat;
    private double waterPointLon;

    public Algorithm(Maps maps) {
        super();
        this.maps = maps;
        init();
    }

    private void init() {
        lengthTab = maps.data.getLengthTab();
        widthTab = maps.data.getWidthTab();
        waterSourcePointLat = maps.data.getWaterSourcePointLat();
        waterSourcePointLon = maps.data.getWaterSourcePointLon();
        waterPointLat = maps.data.getWaterPointLat();
        waterPointLon = maps.data.getWaterPointLon();
        sourceWaterHeight = maps.data.getSourceWaterHeight();

    }

//    public WorldWindow getWwd() {
//        return wwd;
//    }
    void setMaps(Maps _maps) {
        maps = _maps;
        init();
    }

//    void setMaps(Maps maps) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
    public double getWaterPointLat() {
        return waterPointLat;
    }

    public double getWaterPointLon() {
        return waterPointLon;
    }

    public Double[][] getElevationsMap() {
        return elevationsMap;
    }

    public ArrayList<Integer> getCoordinateListInteger() {
        return coordinateListInteger;
    }

    public ArrayList<Double> getListOfFloodValueCopy() {
        return listOfFloodValueCopy;
    }

    public ArrayList<Double> getListOfFloodCoordinateCopy() {
        return listOfFloodCoordinateCopy;
    }

    public double getWaterSourcePointLat() {
        return waterSourcePointLat;
    }

    public double getWaterSourcePointLon() {
        return waterSourcePointLon;
    }

    public int getLengthTab() {
        return lengthTab;
    }

    public int getWidthTab() {
        return widthTab;
    }

    public PositionHolder getStartPosition() {
        return new PositionHolder(waterPointLat, waterPointLon);
    }

    public Boolean[][] getWaterDirectionMap() {
        return waterDirectionMap;
    }

    public ArrayList<Double> getListOfFloodValue() {
        return listOfFloodValue;
    }

    public ArrayList<Double> getCoordinateListDouble() {
        return coordinateListDouble;
    }

    public void calculation(ArrayList<Integer> coordinateListInteger, Double[][] elevationsMap, ArrayList<Double> coordinateListDouble, ArrayList<Double> listOfFloodValue, Boolean[][] booleanElevationsMap, ArrayList<Double> listOfFloodCoordinate, Boolean[][] waterDirectionMap) {
        booleanElevationsMap[(int) abs(waterPointLat)][(int) abs(waterPointLon)] = true;
        waterDirectionMap[(int) abs(waterPointLat)][(int) abs(waterPointLon)] = true;

        for (int i = 0; i < coordinateListDouble.size(); i += 2) {

            if (elevationsMap[(int) (abs(waterPointLat) - coordinateListInteger.get(i))][(int) (abs(waterPointLon) - coordinateListInteger.get(i + 1))] < sourceWaterHeight) {

                listOfFloodCoordinate.add(elevationsMap[(int) (abs(waterPointLat) - coordinateListInteger.get(i))][(int) (abs(waterPointLon) - coordinateListInteger.get(i + 1))]);
                listOfFloodValue.add((waterPointLat - coordinateListInteger.get(i)));	//wps. X nowego zalanego punktu
                listOfFloodValue.add((waterPointLon - coordinateListInteger.get(i + 1)));	//wsp. Y nowego zalanego punktu
                booleanElevationsMap[(int) (abs(waterPointLat) - coordinateListInteger.get(i))][(int) (abs(waterPointLon) - coordinateListInteger.get(i + 1))] = true;
                waterDirectionMap[(int) (abs(waterPointLat) - coordinateListInteger.get(i))][(int) (abs(waterPointLon) - coordinateListInteger.get(i + 1))] = true;

            } else {
                booleanElevationsMap[(int) (abs(waterPointLat) - coordinateListInteger.get(i))][(int) (abs(waterPointLon) - coordinateListInteger.get(i + 1))] = true;
            }
        }
    }

    public void calculation2(ArrayList<Integer> coordinateListInteger, Double[][] elevationsMap, ArrayList<Double> coordinateListDouble, ArrayList<Double> listOfFloodValue, Boolean[][] booleanElevationsMap, ArrayList<Double> listOfFloodCoordinate, Boolean[][] waterDirectionMap) {
        try {
            waterPointLat = listOfFloodValue.get(0);
            waterPointLon = listOfFloodValue.get(1);
            listOfFloodCoordinate.remove(0);
            listOfFloodValue.remove(1);
            listOfFloodValue.remove(0);
        } catch (IndexOutOfBoundsException e) {
            Logging.logger().info("Podana wysokość wody jest mniejsza od wysokości zródła wody! \n");
        }

        for (int i = 0; i < coordinateListDouble.size(); i += 2) {
            try {
                if (booleanElevationsMap[(int) (abs(waterPointLat) - coordinateListInteger.get(i))][(int) (abs(waterPointLon) - coordinateListInteger.get(i + 1))] == false) {
                    if (elevationsMap[(int) (abs(waterPointLat) - coordinateListInteger.get(i))][(int) (abs(waterPointLon) - coordinateListInteger.get(i + 1))] < sourceWaterHeight) {

                        listOfFloodCoordinate.add(elevationsMap[(int) (abs(waterPointLat) - coordinateListInteger.get(i))][(int) (abs(waterPointLon) - coordinateListInteger.get(i + 1))]);
                        listOfFloodValue.add(waterPointLat - coordinateListInteger.get(i));
                        listOfFloodValue.add(waterPointLon - coordinateListInteger.get(i + 1));
                        booleanElevationsMap[(int) (abs(waterPointLat) - coordinateListInteger.get(i))][(int) (abs(waterPointLon) - coordinateListInteger.get(i + 1))] = true;
                        waterDirectionMap[(int) (abs(waterPointLat) - coordinateListInteger.get(i))][(int) (abs(waterPointLon) - coordinateListInteger.get(i + 1))] = true;
                    } else {
                        booleanElevationsMap[(int) (abs(waterPointLat) - coordinateListInteger.get(i))][(int) (abs(waterPointLon) - coordinateListInteger.get(i + 1))] = true;
                    }

                } else {
                    booleanElevationsMap[(int) (abs(waterPointLat) - coordinateListInteger.get(i))][(int) (abs(waterPointLon) - coordinateListInteger.get(i + 1))] = true;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                Logging.logger().info("Wyszedłeś po za obszar!\n");
            }
        }

    }

    public void startCalculation() {
        Double[][] elevationsMap = maps.elevationsMap();
        ArrayList<Double> coordinateListDouble = maps.setCoordinateListDouble();
        ArrayList<Double> listOfFloodValue = maps.listOfFloodValue();
        Boolean[][] booleanElevationsMap = maps.booleanElevationsMap();
        ArrayList<Double> listOfFloodCoordinate = maps.listOfFloodCoordinate();
        Boolean[][] waterDirectionMap = maps.waterDirectionMap();
        ArrayList<Integer> CoordinateListInteger = maps.setCoordinateListInteger();

        calculation(CoordinateListInteger, elevationsMap, coordinateListDouble, listOfFloodValue, booleanElevationsMap, listOfFloodCoordinate, waterDirectionMap);
        do {
            calculation2(CoordinateListInteger, elevationsMap, coordinateListDouble, listOfFloodValue, booleanElevationsMap, listOfFloodCoordinate, waterDirectionMap);
        } while (listOfFloodCoordinate.isEmpty() == false && listOfFloodValue.isEmpty() == false);

        this.waterDirectionMap = waterDirectionMap;
        this.coordinateListDouble = coordinateListDouble;
        this.listOfFloodValue = listOfFloodValue;
        this.elevationsMap = elevationsMap;
        
    }

    public SectorData getMaxMinLatLon() {

        if (maps.data.getRightTopPointLatSource() > maps.data.getLeftBottomPointLatSource()) {
            maxGeoLat = maps.data.getRightTopPointLatSource();
            minGeoLat = maps.data.getLeftBottomPointLatSource();
        } else {
            maxGeoLat = maps.data.getLeftBottomPointLatSource();
            minGeoLat = maps.data.getRightTopPointLatSource();
        }

        if (maps.data.getRightTopPointLonSource() > maps.data.getLeftBottomPointLonSource()) {
            maxGeoLon = maps.data.getRightTopPointLonSource();
            minGeoLon = maps.data.getLeftBottomPointLonSource();
        } else {
            maxGeoLon = maps.data.getLeftBottomPointLonSource();
            minGeoLon = maps.data.getRightTopPointLonSource();
        }
        return new SectorData(new PositionHolder(minGeoLat, minGeoLon), new PositionHolder(maxGeoLat, maxGeoLon));

    }

}
