package org.igniterealtime.restclient.enums;

import jakarta.ws.rs.core.MediaType;

public enum SupportedMediaType {
    
    JSON(MediaType.APPLICATION_JSON_TYPE),
    XML(MediaType.APPLICATION_XML_TYPE);
    
    private MediaType mediaType;
    
    SupportedMediaType(final MediaType mediaType) {
        this.mediaType = mediaType;
    }

    public MediaType getMediaType() {
        return mediaType;
    }
    
}
