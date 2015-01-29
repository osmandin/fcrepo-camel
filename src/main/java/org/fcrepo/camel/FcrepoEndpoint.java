/**
 * Copyright 2014 DuraSpace, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.fcrepo.camel;

import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.api.management.ManagedResource;
import org.apache.camel.api.management.ManagedAttribute;
import org.apache.camel.impl.DefaultEndpoint;
import org.apache.camel.spi.UriEndpoint;

/**
 * Represents a Fcrepo endpoint.
 * @author Aaron Coburn
 * @since October 20, 2014
 */
@ManagedResource(description = "Managed FcrepoEndpoint")
@UriEndpoint(scheme = "fcrepo")
public class FcrepoEndpoint extends DefaultEndpoint {

    private FcrepoConfiguration configuration;

    /**
     * Create a FcrepoEndpoint with a uri, path and component
     * @param uri the endpoint uri (without path values)
     * @param remaining any path values on the endpoint uri
     * @param component an existing component value
     */
    public FcrepoEndpoint(final String uri, final String remaining, final FcrepoComponent component,
            final FcrepoConfiguration configuration) {
        super(uri, component);
        this.configuration = configuration;
        this.setBaseUrl(remaining);
    }

    /**
     * Create a producer endpoint.
     */
    @Override
    public Producer createProducer() {
        return new FcrepoProducer(this);
    }

    /**
     * This component does not implement a consumer endpoint.
     */
    @Override
    public Consumer createConsumer(final Processor processor) {
        throw new RuntimeCamelException("Cannot produce to a FcrepoEndpoint: " + getEndpointUri());
    }

    /**
     * Define the component as a singleton
     */
    @Override
    public boolean isSingleton() {
        return true;
    }

    /**
     * configuration getter
     */
    public FcrepoConfiguration getConfiguration() {
        return configuration;
    }

    /**
     * configuration setter
     * @param config The FcrepoConfiguration
     */
    public void setConfiguration(final FcrepoConfiguration config) {
        this.configuration = config;
    }

    /**
     * baseUrl setter
     * @param url the baseUrl string
     */
    public void setBaseUrl(final String url) {
        getConfiguration().setBaseUrl(url);
    }

    /**
     * baseUrl getter
     */
    public String getBaseUrl() {
        return getConfiguration().getBaseUrl();
    }

    /**
     * accept setter
     * @param type the content-type for Accept headers
     */
    @ManagedAttribute(description = "Accept: Header")
    public void setAccept(final String type) {
        getConfiguration().setAccept(type.replaceAll(" ", "+"));
    }

    /**
     * accept getter
     */
    @ManagedAttribute(description = "Accept: Header")
    public String getAccept() {
        return getConfiguration().getAccept();
    }

    /**
     * contentType setter
     * @param type the content-type for Content-Type headers
     */
    @ManagedAttribute(description = "Content-Type: Header")
    public void setContentType(final String type) {
        getConfiguration().setContentType(type);
    }

    /**
     * contentType getter
     */
    @ManagedAttribute(description = "Content-Type: Header")
    public String getContentType() {
        return getConfiguration().getContentType();
    }

    /**
     * authUsername setter
     * @param username used for authentication
     */
    @ManagedAttribute(description = "Username for authentication")
    public void setAuthUsername(final String username) {
        getConfiguration().setAuthUsername(username);
    }

    /**
     * authUsername getter
     */
    @ManagedAttribute(description = "Username for authentication")
    public String getAuthUsername() {
        return getConfiguration().getAuthUsername();
    }

    /**
     * authPassword setter
     * @param password used for authentication
     */
    @ManagedAttribute(description = "Password for authentication")
    public void setAuthPassword(final String password) {
        getConfiguration().setAuthPassword(password);
    }

    /**
     * authPassword getter
     */
    @ManagedAttribute(description = "Password for authentication")
    public String getAuthPassword() {
        return getConfiguration().getAuthPassword();
    }

    /**
     * authHost setter
     * @param host used for authentication
     */
    @ManagedAttribute(description = "Hostname for authentication")
    public void setAuthHost(final String host) {
        getConfiguration().setAuthHost(host);
    }

    /**
     * authHost getter
     */
    @ManagedAttribute(description = "Hostname for authentication")
    public String getAuthHost() {
        return getConfiguration().getAuthHost();
    }

    /**
     * metadata setter
     * @param metadata whether to retrieve rdf metadata for non-rdf nodes
     */
    @ManagedAttribute(description = "Whether to retrieve the /fcr:metadata endpoint for Binary nodes")
    public void setMetadata(final Boolean metadata) {
        getConfiguration().setMetadata(metadata);
    }

    /**
     * metadata getter
     */
    @ManagedAttribute(description = "Whether to retrieve the /fcr:metadata endpoint for Binary nodes")
    public Boolean getMetadata() {
        return getConfiguration().getMetadata();
    }

    /**
     * throwExceptionOnFailure setter
     * @param throwOnFailure whether non-2xx HTTP response codes throw exceptions
     */
    @ManagedAttribute(description = "Whether non 2xx response codes should throw an exception")
    public void setThrowExceptionOnFailure(final Boolean throwOnFailure) {
        getConfiguration().setThrowExceptionOnFailure(throwOnFailure);
    }

    /**
     * throwExceptionOnFailure getter
     */
    @ManagedAttribute(description = "Whether non 2xx response codes should throw an exception")
    public Boolean getThrowExceptionOnFailure() {
        return getConfiguration().getThrowExceptionOnFailure();
    }

    /**
     * transform setter
     * @param transform define an LD-Path transform program for converting RDF to JSON
     */
    @ManagedAttribute(description = "The LDPath transform program to use")
    public void setTransform(final String transform) {
        getConfiguration().setTransform(transform);
    }

    /**
     * transform getter
     */
    @ManagedAttribute(description = "The LDPath transform program to use")
    public String getTransform() {
        return getConfiguration().getTransform();
    }

    /**
     * tombstone setter
     * @param tombstone whether to access the /fcr:tombstone endpoint for a resource
     */
    @ManagedAttribute(description = "Whether to use the /fcr:tombstone endpoint on objects")
    public void setTombstone(final Boolean tombstone) {
        getConfiguration().setTombstone(tombstone);
    }

    /**
     * tombstone getter
     */
    @ManagedAttribute(description = "Whether to use the /fcr:tombstone endpoint on objects")
    public Boolean getTombstone() {
        return getConfiguration().getTombstone();
    }

    /**
     * preferInclude setter
     */
    @ManagedAttribute(description = "Whether to include a Prefer: return=representation; include=\"URI\" header")
    public void setPreferInclude(final String include) {
        getConfiguration().setPreferInclude(include);
    }

    /**
     * preferInclude getter
     */
    @ManagedAttribute(description = "Whether to include a Prefer: return=representation; include=\"URI\" header")
    public String getPreferInclude() {
        return getConfiguration().getPreferInclude();
    }

    /**
     * preferOmit setter
     */
    @ManagedAttribute(description = "Whether to include a Prefer: return=representation; omit=\"URI\" header")
    public void setPreferOmit(final String omit) {
        getConfiguration().setPreferOmit(omit);
    }

    /**
     * preferOmit getter
     */
    @ManagedAttribute(description = "Whether to include a Prefer: return=representation; omit=\"URI\" header")
    public String getPreferOmit() {
        return getConfiguration().getPreferOmit();
    }
}
