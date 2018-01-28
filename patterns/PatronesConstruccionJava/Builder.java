import java.util.*;

public class Builder {

	    public static void main(String[] args) {
	       
	       HouseBuilder one = new CasaUnaPlanta("2 dormaterios, 2.5 ba–os, Garaje para 2 coches, 1500 metros cuadros");
	       HouseBuilder two = new CasaDosPlantas("4 dormaterios, 4 ba–os, Garaje para 3 coches, 3000 metros cuadros");

	       WorkShop shop = new WorkShop();
	       shop.construct(one);
	       shop.construct(two);
	    
	   
	       System.out.println("Comprobar construcci—n de la casa: \n");
	       System.out.println(one.showProgress());
	       System.out.println(two.showProgress());
	   }
	}
	
	class WorkShop {
	    public void construct(HouseBuilder hb) {
	        hb.buildFoundation();
	        hb.buildFrame();
	        hb.buildExterior();
	        hb.buildInterior();
	    }
	}

	abstract class HouseBuilder {
	    protected House house = new House();
	    
	    protected String showProgress() {
	        return house.toString();
	    }

	    abstract public void buildFoundation();
	    abstract public void buildFrame();
	    abstract public void buildExterior();
	    abstract public void buildInterior();
	}

	class CasaUnaPlanta extends HouseBuilder {
	    
	    public CasaUnaPlanta(String features) {
	        house.setType(this.getClass() + " " + features);
	    }
	    public void buildFoundation() {
	        //doEngineering()
	        //doExcavating()
	        //doPlumbingHeatingElectricity()
	        //doSewerWaterHookUp()
	        //doFoundationInspection()
	        house.setProgress("fundaci—n terminada");
	    }

	    public void buildFrame() {
	        //doHeatingPlumbingRoof()
	        //doElectricityRoute()
	        //doDoorsWindows()
	        //doFrameInspection()
	        house.setProgress("marco terminado");
	    }

	    public void buildExterior() {
	        //doOverheadDoors()
	        //doBrickWorks()
	        //doSidingsoffitsGutters()
	        //doDrivewayGarageFloor()
	        //doDeckRail()
	        //doLandScaping()
	        house.setProgress("Exterior terminado");
	    }

	    public void buildInterior() {
	        //doAlarmPrewiring()
	        //doBuiltinVacuum()
	        //doInsulation()
	        //doDryWall()
	        //doPainting()
	        //doLinoleum()
	        //doCabinet()
	        //doTileWork()
	        //doLightFixtureBlinds()
	        //doCleaning()
	        //doInteriorInspection()
	        house.setProgress("Interior en construcci—n");
	    } 
	}

	class CasaDosPlantas extends HouseBuilder {
	  
	    public CasaDosPlantas(String features) {
	        house.setType(this.getClass() + " " + features);
	    }
	    public void buildFoundation() {
	        //doEngineering()
	        //doExcavating()
	        //doPlumbingHeatingElectricity()
	        //doSewerWaterHookUp()
	        //doFoundationInspection()
	        house.setProgress("fundaci—n terminada");
	    }

	    public void buildFrame() {
	        //doHeatingPlumbingRoof()
	        //doElectricityRoute()
	        //doDoorsWindows()
	        //doFrameInspection()
	        house.setProgress("marco en construcci—n");
	    }

	    public void buildExterior() {
	        //doOverheadDoors()
	        //doBrickWorks()
	        //doSidingsoffitsGutters()
	        //doDrivewayGarageFloor()
	        //doDeckRail()
	        //doLandScaping()
	        house.setProgress("Exterior no empezado todav’a");
	    }

	    public void buildInterior() {
	        //doAlarmPrewiring()
	        //doBuiltinVacuum()
	        //doInsulation()
	        //doDryWall()
	        //doPainting()
	        //doLinoleum()
	        //doCabinet()
	        //doTileWork()
	        //doLightFixtureBlinds()
	        //doCleaning()
	        //doInteriorInspection()
	        house.setProgress("Interior no empezado todav’a");
	    }
	}

	class House {
	    private String type = null;
	    private List features = new ArrayList();

	    public House() {

	    }

	    public House(String type) {
	        this.type = type;
	    }

	    public void setType(String type) {
	        this.type = type;
	    }

	    public String getType() {
	        return type;
	    }

	    public void setProgress(String s) {
	        features.add(s);
	    }

	    public String toString() {
	        StringBuffer ff = new StringBuffer();
	        String t = type.substring(6);
	        ff.append(t + "\n ");
	        for (int i = 0; i < features.size(); i ++) {
	             ff.append(features.get(i) + "\n ");
	        }
	        return ff.toString();
	    }
	}
