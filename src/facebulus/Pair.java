/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facebulus;

/**
 *
 * @author dttri
 */
public class Pair<Integer, Double> {
    
    private Integer predictedLabel;
    private Double confidenceValue;
    public Pair(Integer n, Double m)
    {
        this.predictedLabel = n;
        this.confidenceValue = m;       
    }
    
    public Integer getPredictedLabel()
    {
        return predictedLabel;
    }
    
    public Double getConfidenceValue()
    {
        return confidenceValue;
    }
}
