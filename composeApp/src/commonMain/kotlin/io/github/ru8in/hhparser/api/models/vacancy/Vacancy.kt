package io.github.ru8in.hhparser.api.models.vacancy

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class Vacancy(
    @SerialName("accept_handicapped") val acceptHandicapped: Boolean? = null,
    @SerialName("accept_incomplete_resumes") val acceptIncompleteResumes: Boolean? = null,
    @SerialName("accept_kids") val acceptKids: Boolean? = null,
    @SerialName("accept_temporary") val acceptTemporary: Boolean? = null,
    @SerialName("alternate_url") val alternateUrl: String,
    @SerialName("apply_alternate_url") val applyAlternateUrl: String,
    @SerialName("approved") val approved: Boolean? = null,
    @SerialName("archived") val archived: Boolean,
    @SerialName("area") val area: Area,
    @SerialName("billing_tpe") val billingType: BillingType? = null,
    @SerialName("contacts") val contacts: Contacts? = null,
    @SerialName("department") val department: Department? = null,
    @SerialName("description") val description: String? = null,
    @SerialName("driver_license_types") val driverLicenseTypes: List<Int>? = null,
    @SerialName("employer") val employer: Employer? = null,
    @SerialName("employment_form") val employmentForm: EmploymentForm? = null,
    @SerialName("experience") val experience: Experience? = null,
    @SerialName("fly_in_fly_out_duration") val flyInFlyOutDuration: List<Pair<Int, String>>? = null,
    @SerialName("has_test") val hasTest: Boolean,
    @SerialName("id") val id: Int,
    @SerialName("initial_created_at") val initialCreatedAt: String? = null,
    @SerialName("insider_interview") val insiderInterview: JsonObject? = null,
    @SerialName("internship") val internship: Boolean? = null,
    @SerialName("key_skills") val keySkills: List<Map<String, String>>? = null,
    @SerialName("languages") val languages: List<Language>? = null,
    @SerialName("name") val name: String,
    @SerialName("negotiations_url") val negotiationsUrl: String? = null,
    @SerialName("night_shifts") val nightShifts: Boolean? = null,
    @SerialName("premium") val premium: Boolean,
    @SerialName("professional_roles") val professionalRoles: List<JsonObject>,
    @SerialName("published_at") val publishedAt: String,
    @SerialName("relations") val relations: List<Relations>? = null,
    @SerialName("response_letter_required") val responseLetterRequired: Boolean,
    @SerialName("response_url") val responseUrl: String? = null,
    @SerialName("salary") val salary: Salary? = null,
    @SerialName("suitable_resumes_url") val suitableResumesUrl: String? = null,
    @SerialName("test") val test: JsonObject? = null,
    @SerialName("type") val type: VacancyType? = null,
    @SerialName("vacancy_properties") val vacancyProperties: JsonObject? = null,
    @SerialName("video_vacancy") val videoVacancy: JsonObject? = null,
    @SerialName("work_format") val workFormat: List<WorkFormat>? = null,
    @SerialName("work_schedule_by_days") val workScheduleByDays: List<Map<String, String>>? = null,
    @SerialName("working_hours") val workingHours: List<Map<String, String>>? = null,
    @SerialName("address") val address: Address? = null
)
