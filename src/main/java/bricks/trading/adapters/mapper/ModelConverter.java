package bricks.trading.adapters.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class ModelConverter {

    @Autowired
    protected ModelMapper mapper;

    public <T> Object map(Object data, Class<T> reference) { return this.mapper.map(data, reference);}
}
