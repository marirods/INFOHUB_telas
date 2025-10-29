package com.example.infohub_telas.model

enum class CompanyStatus {
    ACTIVE, INACTIVE, PENDING
}

data class Company(
    val id: String,
    val name: String,
    val cnpj: String,
    val status: CompanyStatus,
    val revenue: Double = 0.0,
    val promotionsCount: Int = 0,
    val salesCount: Int = 0,
    val rating: Float = 0f
)

data class CompanyMetrics(
    val totalCompanies: Int,
    val activeCompanies: Int,
    val totalPromotions: Int,
    val averageRevenue: Double,
    val companiesData: List<Company>
)

// Mock data for preview and testing
object CompanyMockData {
    val sampleCompanies = listOf(
        Company(
            id = "1",
            name = "Tech Solutions Ltd",
            cnpj = "12.345.678/0001-90",
            status = CompanyStatus.ACTIVE,
            revenue = 150000.0,
            promotionsCount = 5,
            salesCount = 120,
            rating = 4.5f
        ),
        Company(
            id = "2",
            name = "Digital Innovations SA",
            cnpj = "98.765.432/0001-10",
            status = CompanyStatus.PENDING,
            revenue = 75000.0,
            promotionsCount = 2,
            salesCount = 45,
            rating = 4.0f
        ),
        Company(
            id = "3",
            name = "Global Services LTDA",
            cnpj = "45.678.901/0001-23",
            status = CompanyStatus.INACTIVE,
            revenue = 25000.0,
            promotionsCount = 0,
            salesCount = 10,
            rating = 3.5f
        )
    )

    val sampleMetrics = CompanyMetrics(
        totalCompanies = 3,
        activeCompanies = 1,
        totalPromotions = 7,
        averageRevenue = 83333.33,
        companiesData = sampleCompanies
    )
}
