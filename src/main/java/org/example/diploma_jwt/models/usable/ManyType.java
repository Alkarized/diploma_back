package org.example.diploma_jwt.models.usable;

import org.example.diploma_jwt.interfaces.ManyItemsGettable;

import java.util.Collections;
import java.util.List;

public enum ManyType implements ManyItemsGettable { //todo every many choose yourself!
    MAX {
        @Override
        public Double getManyItemsPrice(List<Double> prices) {
            return Collections.max(prices);
        }
    }, MIN {
        @Override
        public Double getManyItemsPrice(List<Double> prices) {
            return Collections.min(prices);
        }
    }, SUM {
        @Override
        public Double getManyItemsPrice(List<Double> prices) {
            return prices.stream().mapToDouble(Double::doubleValue).sum();
        }
    }, MEAN {
        @Override
        public Double getManyItemsPrice(List<Double> prices) {
            return prices.stream().mapToDouble(Double::doubleValue).sum()/prices.size();
        }
    }
}
