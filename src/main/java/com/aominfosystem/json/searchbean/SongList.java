package com.aominfosystem.json.searchbean;

import com.fasterxml.jackson.annotation.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "album",
        "artists",
        "name",
        "id",
        "needPay"
})
public class SongList {

    @JsonProperty("album")
    private Album album;
    @JsonProperty("artists")
    private List<Artist> artists = new ArrayList<Artist>();
    @JsonProperty("name")
    private String name;
    @JsonProperty("id")
    private String id;
    @JsonProperty("needPay")
    private Boolean needPay;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     *     The album
     */
    @JsonProperty("album")
    public Album getAlbum() {
        return album;
    }

    /**
     *
     * @param album
     *     The album
     */
    @JsonProperty("album")
    public void setAlbum(Album album) {
        this.album = album;
    }

    /**
     *
     * @return
     *     The artists
     */
    @JsonProperty("artists")
    public List<Artist> getArtists() {
        return artists;
    }

    /**
     *
     * @param artists
     *     The artists
     */
    @JsonProperty("artists")
    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }

    /**
     *
     * @return
     *     The name
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     *     The name
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     *     The id
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     *     The id
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     *     The needPay
     */
    @JsonProperty("needPay")
    public Boolean getNeedPay() {
        return needPay;
    }

    /**
     *
     * @param needPay
     *     The needPay
     */
    @JsonProperty("needPay")
    public void setNeedPay(Boolean needPay) {
        this.needPay = needPay;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(album).append(artists).append(name).append(id).append(needPay).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof SongList) == false) {
            return false;
        }
        SongList rhs = ((SongList) other);
        return new EqualsBuilder().append(album, rhs.album).append(artists, rhs.artists).append(name, rhs.name).append(id, rhs.id).append(needPay, rhs.needPay).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}