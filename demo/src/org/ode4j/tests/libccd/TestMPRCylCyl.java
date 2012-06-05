package org.ode4j.tests.libccd;

import static org.cpp4j.Cstdio.*;
import static org.junit.Assert.*;
import static org.ode4j.ode.internal.libccd.CCD.*;
import static org.ode4j.ode.internal.libccd.CCDMPR.*;
import static org.ode4j.ode.internal.libccd.CCDQuat.*;
import static org.ode4j.ode.internal.libccd.CCDVec3.*;
import static org.ode4j.tests.libccd.CCDTestCommon.*;
import static org.ode4j.tests.libccd.CCDTestSupport.*;

import org.cpp4j.java.RefDouble;
import org.junit.Test;
import org.ode4j.ode.internal.libccd.CCD.ccd_t;

public class TestMPRCylCyl {

	@Test
	public void mprCylcylAlignedX()
	{
	    ccd_t ccd = new ccd_t();
	    ccd_cyl_t c1 = CCD_CYL();
	    ccd_cyl_t c2 = CCD_CYL();
	    int i;
	    int res;

	    CCD_INIT(ccd);
	    ccd.support1 = ccdSupport;
	    ccd.support2 = ccdSupport;
	    ccd.center1  = ccdObjCenter;
	    ccd.center2  = ccdObjCenter;

	    c1.radius = 0.35;
	    c1.height = 0.5;
	    c2.radius = 0.5;
	    c2.height = 1.;

	    ccdVec3Set(c1.pos, -5., 0., 0.);
	    for (i = 0; i < 100; i++){
	        res = ccdMPRIntersect(c1, c2, ccd);

	        if (i < 42 || i > 58){
	            assertFalse(res!=0);
	        }else{
	            assertTrue(res!=0);
	        }

	        c1.pos.add(0, 0.1);
	    }
	}

	@Test
	public void mprCylcylAlignedY()
	{
	    ccd_t ccd = new ccd_t();
	    ccd_cyl_t c1 = CCD_CYL();
	    ccd_cyl_t c2 = CCD_CYL();
	    int i;
	    int res;

	    CCD_INIT(ccd);
	    ccd.support1 = ccdSupport;
	    ccd.support2 = ccdSupport;
	    ccd.center1  = ccdObjCenter;
	    ccd.center2  = ccdObjCenter;

	    c1.radius = 0.35;
	    c1.height = 0.5;
	    c2.radius = 0.5;
	    c2.height = 1.;

	    ccdVec3Set(c1.pos, 0., -5., 0.);
	    for (i = 0; i < 100; i++){
	        res = ccdMPRIntersect(c1, c2, ccd);

	        if (i < 42 || i > 58){
	            assertFalse(res!=0);
	        }else{
	            assertTrue(res!=0);
	        }

	        c1.pos.add(1, 0.1);
	    }
	}

	@Test
	public void mprCylcylAlignedZ()
	{
	    ccd_t ccd = new ccd_t();
	    ccd_cyl_t c1 = CCD_CYL();
	    ccd_cyl_t c2 = CCD_CYL();
	    int i;
	    int res;

	    CCD_INIT(ccd);
	    ccd.support1 = ccdSupport;
	    ccd.support2 = ccdSupport;
	    ccd.center1  = ccdObjCenter;
	    ccd.center2  = ccdObjCenter;

	    c1.radius = 0.35;
	    c1.height = 0.5;
	    c2.radius = 0.5;
	    c2.height = 1.;

	    ccdVec3Set(c1.pos, 0., 0., -5.);
	    for (i = 0; i < 100; i++){
	        res = ccdMPRIntersect(c1, c2, ccd);

	        if (i < 43 || i > 57){
	            assertFalse(res!=0);
	        }else{
	            assertTrue(res!=0);
	        }

	        c1.pos.add(2, 0.1);
	    }
	}

//	#define TOSVT() \
//	    svtObjPen(cyl1, cyl2, stdout, "Pen 1", depth, dir, pos); \
//	    ccdVec3Scale(dir, depth); \
//	    ccdVec3Add(cyl2.pos, dir); \
//	    svtObjPen(cyl1, cyl2, stdout, "Pen 1", depth, dir, pos)

	@Test
	public void mprCylcylPenetration()
	{
	    ccd_t ccd = new ccd_t();
	    ccd_cyl_t cyl1 = CCD_CYL();
	    ccd_cyl_t cyl2 = CCD_CYL();
	    int res;
	    ccd_vec3_t axis = new ccd_vec3_t();
	    RefDouble depth = new RefDouble();
	    ccd_vec3_t dir = new ccd_vec3_t(), pos = new ccd_vec3_t();

	    fprintf(stderr, "\n\n\n---- mprCylcylPenetration ----\n\n\n");

	    cyl1.radius = 0.35;
	    cyl1.height = 0.5;
	    cyl2.radius = 0.5;
	    cyl2.height = 1.;

	    CCD_INIT(ccd);
	    ccd.support1 = ccdSupport;
	    ccd.support2 = ccdSupport;
	    ccd.center1  = ccdObjCenter;
	    ccd.center2  = ccdObjCenter;

	    ccdVec3Set(cyl2.pos, 0., 0., 0.3);
	    res = ccdMPRPenetration(cyl1, cyl2, ccd, depth, dir, pos);
	    assertTrue(res == 0);
	    recPen(depth, dir, pos, stdout, "Pen 1");
	    //TOSVT();

	    ccdVec3Set(cyl1.pos, 0.3, 0.1, 0.1);
	    res = ccdMPRPenetration(cyl1, cyl2, ccd, depth, dir, pos);
	    assertTrue(res == 0);
	    recPen(depth, dir, pos, stdout, "Pen 2");
	    //TOSVT();

	    ccdVec3Set(axis, 0., 1., 1.);
	    ccdQuatSetAngleAxis(cyl2.quat, M_PI / 4., axis);
	    ccdVec3Set(cyl2.pos, 0., 0., 0.);
	    res = ccdMPRPenetration(cyl1, cyl2, ccd, depth, dir, pos);
	    assertTrue(res == 0);
	    recPen(depth, dir, pos, stdout, "Pen 3");
	    //TOSVT();

	    ccdVec3Set(axis, 0., 1., 1.);
	    ccdQuatSetAngleAxis(cyl2.quat, M_PI / 4., axis);
	    ccdVec3Set(cyl2.pos, -0.2, 0.7, 0.2);
	    res = ccdMPRPenetration(cyl1, cyl2, ccd, depth, dir, pos);
	    assertTrue(res == 0);
	    recPen(depth, dir, pos, stdout, "Pen 4");
	    //TOSVT();

	    ccdVec3Set(axis, 0.567, 1.2, 1.);
	    ccdQuatSetAngleAxis(cyl2.quat, M_PI / 4., axis);
	    ccdVec3Set(cyl2.pos, 0.6, -0.7, 0.2);
	    res = ccdMPRPenetration(cyl1, cyl2, ccd, depth, dir, pos);
	    assertTrue(res == 0);
	    recPen(depth, dir, pos, stdout, "Pen 5");
	    //TOSVT();

	    ccdVec3Set(axis, -4.567, 1.2, 0.);
	    ccdQuatSetAngleAxis(cyl2.quat, M_PI / 3., axis);
	    ccdVec3Set(cyl2.pos, 0.6, -0.7, 0.2);
	    res = ccdMPRPenetration(cyl1, cyl2, ccd, depth, dir, pos);
	    assertTrue(res == 0);
	    recPen(depth, dir, pos, stdout, "Pen 6");
	    //TOSVT();
	}

}
