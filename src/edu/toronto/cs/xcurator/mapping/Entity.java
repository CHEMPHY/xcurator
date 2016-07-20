/*
 *    Copyright (c) 2013, University of Toronto.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License"); you may
 *    not use this file except in compliance with the License. You may obtain
 *    a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *    WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *    License for the specific language governing permissions and limitations
 *    under the License.
 */
package edu.toronto.cs.xcurator.mapping;

import edu.toronto.cs.xcurator.common.NsContext;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.w3c.dom.Element;

public class Entity implements MappingModel {

    NsContext namespaceContext;

    // The XML type URI of this entity
    String xmlTypeUri;

    Map<String, Relation> relations;

    Map<String, Attribute> attributes;

    SearchPath paths;

    String rdfTypeUri;

    // The xml instances of this entity
    Set<Element> instances;

    // The name of the entity, used to construct relation
    String name;

    public Entity(String rdfTypeUri, String xmlTypeUri, NsContext nsContext,
            String name) {
        this(rdfTypeUri, xmlTypeUri, nsContext);
        this.name = name;
    }

    public Entity(String rdfTypeUri, String xmlTypeUri, NsContext nsContext) {
        this.rdfTypeUri = rdfTypeUri;
        this.paths = new SearchPath();
        this.instances = new HashSet<>();
        this.namespaceContext = nsContext;
        relations = new HashMap<>();
        attributes = new HashMap<>();
        this.xmlTypeUri = xmlTypeUri;
    }

    @Override
    /**
     * The entity uses the XML type URI as its ID
     */
    public String getId() {
        return xmlTypeUri;
    }

    @Override
    public String getPath() {
        return paths.getPath();
    }

    @Override
    public void addPath(String path) {
        paths.addPath(path);
    }

    public String getName() {
        return name;
    }

    public void addInstance(Element element) {
        this.instances.add(element);
    }

    public void addAttribute(Attribute attr) {
        Attribute existAttr = attributes.get(attr.getId());
        if (existAttr != null) {
            existAttr.addPath(attr.getPath());
            existAttr.addInstances(attr.getInstances());
            return;
        }
        attributes.put(attr.getId(), attr);
    }

    public void addRelation(Relation rl) {
        Relation existRel = relations.get(rl.getId());
        if (existRel != null) {
            existRel.addPath(rl.getPath());
            return;
        }
        relations.put(rl.getId(), rl);
    }

    public void mergeNamespaceContext(NsContext nsContext, boolean override) {
        this.namespaceContext.merge(nsContext, override);
    }

    public boolean hasAttribute(String id) {
        return attributes.containsKey(id);
    }

    public boolean hasRelation(String id) {
        return relations.containsKey(id);
    }

    public Attribute getAttribute(String id) {
        return attributes.get(id);
    }

    public Relation getRelation(String id) {
        return relations.get(id);
    }

    public void removeRelation(String id) {
        relations.remove(id);
    }

    public Iterator<Attribute> getAttributeIterator() {
        return attributes.values().iterator();
    }

    public Iterator<Relation> getRelationIterator() {
        return relations.values().iterator();
    }

    public NsContext getNamespaceContext() {
        return namespaceContext;
    }

    public Iterator<Element> getXmlInstanceIterator() {
        return instances.iterator();
    }

    public int getXmlInstanceCount() {
        return instances.size();
    }

    // Return the XML type from which this entity was extracted
    public String getXmlTypeUri() {
        return xmlTypeUri;
    }

    public String getRdfTypeUri() {
        return rdfTypeUri;
    }

    public void resetRdfTypeUri(String typeUri) {
        rdfTypeUri = typeUri;
    }

}
