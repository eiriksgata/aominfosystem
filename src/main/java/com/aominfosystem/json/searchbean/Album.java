package com.aominfosystem.json.searchbean;

import com.fasterxml.jackson.annotation.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "cover",
        "coverBig",
        "coverSmall",
        "name"
})
public class Album {

    @JsonProperty("id")
    private String id;
    @JsonProperty("cover")
    private String cover;
    @JsonProperty("coverBig")
    private String coverBig;
    @JsonProperty("coverSmall")
    private String coverSmall;
    @JsonProperty("name")
    private String name;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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
     *     The cover
     */
    @JsonProperty("cover")
    public String getCover() {
        return cover;
    }

    /**
     *
     * @param cover
     *     The cover
     */
    @JsonProperty("cover")
    public void setCover(String cover) {
        this.cover = cover;
    }

    /**
     *
     * @return
     *     The coverBig
     */
    @JsonProperty("coverBig")
    public String getCoverBig() {
        return coverBig;
    }

    /**
     *
     * @param coverBig
     *     The coverBig
     */
    @JsonProperty("coverBig")
    public void setCoverBig(String coverBig) {
        this.coverBig = coverBig;
    }

    /**
     *
     * @return
     *     The coverSmall
     */
    @JsonProperty("coverSmall")
    public String getCoverSmall() {
        return coverSmall;
    }

    /**
     *
     * @param coverSmall
     *     The coverSmall
     */
    @JsonProperty("coverSmall")
    public void setCoverSmall(String coverSmall) {
        this.coverSmall = coverSmall;
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
        return new HashCodeBuilder().append(id).append(cover).append(coverBig).append(coverSmall).append(name).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Album) == false) {
            return false;
        }
        Album rhs = ((Album) other);
        return new EqualsBuilder().append(id, rhs.id).append(cover, rhs.cover).append(coverBig, rhs.coverBig).append(coverSmall, rhs.coverSmall).append(name, rhs.name).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
