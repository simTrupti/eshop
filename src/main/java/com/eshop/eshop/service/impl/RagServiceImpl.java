package com.eshop.eshop.service.impl;

import com.eshop.eshop.service.RagService;
import io.qdrant.client.QdrantClient;
import io.qdrant.client.grpc.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RagServiceImpl implements RagService {

    private final QdrantClient qdrantClient;

    @Override
    public void createCollection() {

        try {
            qdrantClient.createCollectionAsync(
                    "products",
                    Collections.VectorParams.newBuilder()
                            .setSize(768)
                            .setDistance(Collections.Distance.Cosine)
                            .build()
            ).get();

            System.out.println("Qdrant collection created: products");
        }
        catch (Exception e) {
            System.out.println(
                    "Qdrant collection already exists or could not be created: "
                            + e.getMessage()
            );
        }
    }

//    @PostConstruct
//    public void init() {
//        createCollection();
//    }


}
