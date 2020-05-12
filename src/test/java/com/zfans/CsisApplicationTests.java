package com.zfans;

import com.zfans.entity.Brand;
import com.zfans.entity.Supplier;
import com.zfans.service.BrandService;
import com.zfans.service.SupplierService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@SpringBootTest
class CsisApplicationTests {
    @Autowired
    private BrandService brandService;
    @Autowired
    private SupplierService supplierService;

    @Test
    void brand() {

//            brandService.saveBrand(brand);
//            brandService.deleteBrandById(1L);

        System.out.println(brandService.getBrandById(2L));
    }

    @Test
    void supplier() {
//        for (int i = 0; i < 10; i++) {
//            supplierService.saveSupplier(new Supplier(Long.valueOf(i + 1), "1", "2", "3", "4"));
//        }
//        supplierService.deleteSupplierById(25L);
        System.out.println(supplierService.getSupplierById(24L));

    }
}
