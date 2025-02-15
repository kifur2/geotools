/*
 *    GeoTools - The Open Source Java GIS Toolkit
 *    http://geotools.org
 *
 *    (C) 2012, Open Source Geospatial Foundation (OSGeo)
 *
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU Lesser General Public
 *    License as published by the Free Software Foundation;
 *    version 2.1 of the License.
 *
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *    Lesser General Public License for more details.
 */
package org.geotools.data.sqlserver;

import static org.junit.Assert.assertEquals;

import org.geotools.api.feature.type.GeometryDescriptor;
import org.geotools.jdbc.JDBCGeometryOnlineTest;
import org.geotools.jdbc.JDBCGeometryTestSetup;
import org.geotools.referencing.CRS;
import org.junit.Test;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryCollection;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.MultiLineString;
import org.locationtech.jts.geom.MultiPoint;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;

/** @author DamianoG */
public class SQLServerGeometryOnlineTest extends JDBCGeometryOnlineTest {

    private SQLServerGeometryTestSetup testSetup;

    @Override
    protected JDBCGeometryTestSetup createTestSetup() {
        testSetup = new SQLServerGeometryTestSetup();
        return testSetup;
    }

    @Override
    public void testPoint() throws Exception {
        testSetup.setupMetadataTable(dataStore);
        assertEquals(Point.class, checkGeometryType(Point.class));
    }

    @Override
    public void testLineString() throws Exception {
        testSetup.setupMetadataTable(dataStore);
        assertEquals(LineString.class, checkGeometryType(LineString.class));
    }

    @Override
    public void testLinearRing() throws Exception {
        // LinearRing is not supported by SQLServer
    }

    @Override
    public void testPolygon() throws Exception {
        testSetup.setupMetadataTable(dataStore);
        assertEquals(Polygon.class, checkGeometryType(Polygon.class));
    }

    @Override
    public void testMultiPoint() throws Exception {
        testSetup.setupMetadataTable(dataStore);
        assertEquals(MultiPoint.class, checkGeometryType(MultiPoint.class));
    }

    @Override
    public void testMultiLineString() throws Exception {
        testSetup.setupMetadataTable(dataStore);
        assertEquals(MultiLineString.class, checkGeometryType(MultiLineString.class));
    }

    @Override
    public void testMultiPolygon() throws Exception {
        testSetup.setupMetadataTable(dataStore);
        assertEquals(MultiPolygon.class, checkGeometryType(MultiPolygon.class));
    }

    @Override
    public void testGeometry() throws Exception {
        testSetup.setupMetadataTable(dataStore);
        assertEquals(Geometry.class, checkGeometryType(Geometry.class));
    }

    @Override
    public void testGeometryCollection() throws Exception {
        testSetup.setupMetadataTable(dataStore);
        assertEquals(GeometryCollection.class, checkGeometryType(GeometryCollection.class));
    }

    @Test
    public void testGeometryMetadataTable() throws Exception {
        testSetup.setupMetadataTable(dataStore);

        GeometryDescriptor gd =
                dataStore.getFeatureSource(tname("gtmeta")).getSchema().getGeometryDescriptor();
        assertEquals(Point.class, gd.getType().getBinding());
        assertEquals(4326, (int) CRS.lookupEpsgCode(gd.getCoordinateReferenceSystem(), false));
    }
}
