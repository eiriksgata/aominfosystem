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
        "success",
        "total",
        "songList"
})
public class ExampleClass {

    @JsonProperty("success")
    private Boolean success;
    @JsonProperty("total")
    private Integer total;
    @JsonProperty("songList")
    private List<SongList> songList = new ArrayList<SongList>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * @return The success
     */
    @JsonProperty("success")
    public Boolean getSuccess() {
        return success;
    }

    /**
     * @param success The success
     */
    @JsonProperty("success")
    public void setSuccess(Boolean success) {
        this.success = success;
    }

    /**
     * @return The total
     */
    @JsonProperty("total")
    public Integer getTotal() {
        return total;
    }

    /**
     * @param total The total
     */
    @JsonProperty("total")
    public void setTotal(Integer total) {
        this.total = total;
    }

    /**
     * @return The songList
     */
    @JsonProperty("songList")
    public List<SongList> getSongList() {
        return songList;
    }

    /**
     * @param songList The songList
     */
    @JsonProperty("songList")
    public void setSongList(List<SongList> songList) {
        this.songList = songList;
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
        return new HashCodeBuilder().append(success).append(total).append(songList).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ExampleClass) == false) {
            return false;
        }
        ExampleClass rhs = ((ExampleClass) other);
        return new EqualsBuilder().append(success, rhs.success).append(total, rhs.total).append(songList, rhs.songList).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
