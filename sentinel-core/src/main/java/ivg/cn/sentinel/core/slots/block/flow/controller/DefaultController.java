package ivg.cn.sentinel.core.slots.block.flow.controller;

import ivg.cn.sentinel.core.slots.block.flow.TrafficShapingController;

public class DefaultController implements TrafficShapingController{

	private double count;
    private int grade;

    public DefaultController(double count, int grade) {
        this.count = count;
        this.grade = grade;
    }
	
}
