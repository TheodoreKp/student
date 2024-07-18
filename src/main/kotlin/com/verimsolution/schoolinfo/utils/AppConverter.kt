package com.verimsolution.schoolinfo.utils

import com.verimsolution.schoolinfo.entities.*
import com.verimsolution.schoolinfo.requests.*
import com.verimsolution.schoolinfo.responses.*
import com.verimsolution.schoolinfo.utils.emuns.CommunicateType
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class AppConverter {

    fun userRequestToUser(request: UserRequest): User {
        val user = User(username = "", password = "", email = "", phone = "")
        return user.copy(
            username = request.username!!,
            password = request.password!!,
            phone = request.phone!!,
            email = request.email!!,
            verify = request.verify!!
        )
    }

    fun userToUserResponse(user: User): UserResponse = UserResponse(
        user.id.toHexString(),
        user.username,
        user.email,
        user.phone,
        user.verify,
        user.emailVerify,
        roles = user.roles.map { RoleResponse(it.id.toHexString(), it.name) },
        schools = user.schools.map { userSchoolToUserSchoolResponse(it) }
    )

    fun userSchoolToUserSchoolResponse(userSchool: UserSchool) = UserSchoolResponse(
        id = userSchool.id.toHexString(),
        school = schoolToSchoolResponse(userSchool.school),
        enable = userSchool.enable
    )

    fun schoolToSchoolResponse(school: School): SchoolResponse = SchoolResponse(
        school.id.toHexString(),
        school.name,
        school.address,
        school.phone,
        school.email,
        school.principal
    )

    fun schoolRequestToSchool(schoolRequest: SchoolRequest): School {
        val school = School(name = "", address = "", phone = "", email = "", principal = "")
        return school.copy(
            name = schoolRequest.name!!,
            address = schoolRequest.address!!,
            phone = schoolRequest.phone!!,
            email = schoolRequest.email!!,
            principal = schoolRequest.principal!!
        )
    }

    fun schoolRequestToSchool(school: School, schoolRequest: SchoolRequest): School = school.copy(
        name = schoolRequest.name!!,
        address = schoolRequest.address!!,
        phone = schoolRequest.phone!!,
        email = schoolRequest.email!!,
        principal = schoolRequest.principal!!
    )


    fun classroomRequestToClassroom(request: ClassroomRequest): Classroom {
        val classroom = Classroom()
        return classroom.copy(
            name = request.name!!,
            description = request.description!!,
            teacher = request.teacher!!
        )
    }

    fun classroomRequestToClassroom(classroom: Classroom, request: ClassroomRequest): Classroom = classroom.copy(
        name = request.name!!,
        description = request.description!!,
        teacher = request.teacher!!
    )


    fun postCategoryRequestToPostCategory(request: PostCategoryRequest): PostCategory {
        val category = PostCategory()
        return category.copy(
            name = request.name!!
        )
    }

    fun postCategoryRequestToPostCategory(category: PostCategory, request: PostCategoryRequest): PostCategory =
        category.copy(
            name = request.name!!
        )

    fun postRequestToPost(request: PostRequest): Post {
        val post = Post()
        return post.copy(
            title = request.title!!,
            introduction = request.introduction!!,
            content = request.content!!,
        )
    }

    fun postRequestToPost(post: Post, request: PostRequest): Post = post.copy(
        title = request.title!!,
        introduction = request.introduction!!,
        content = request.content!!
    )
}

//fun Student.request(request: StudentRequest): Student = this.copy(
//    firstName = request.firstName!!,
//    lastName = request.lastName!!,
//    phone = request.phone!!,
//    birthday = request.birthday!!,
//    genre = request.genre!!
//)

fun Communicate.response(): CommunicateResponse = CommunicateResponse(
    id = id.toHexString(),
    title = title,
    content = content,
    createdAt = createdAt,
    view = view,
    classrooms = classrooms.map { it.response() },
    type = type,
    professor = professor?.response(),
    valid = valid
)

fun School.response(): SchoolResponse = SchoolResponse(
    id = id.toHexString(),
    name = name,
    address = address,
    phone = phone,
    email = email,
    principal = principal
)

fun Classroom.response(): ClassroomResponse = ClassroomResponse(
    id = id.toHexString(),
    name = name,
    description = description,
    teacher = teacher,
    school = school.response(),
    reference = reference,
    updatedAt = updatedAt,
    createdAt = createdAt
)

fun PostCategory.response(): PostCategoryResponse = PostCategoryResponse(
    id = id.toHexString(),
    name = name,
    createdAt = createdAt
)

fun Student.response(): StudentResponse = StudentResponse(
    id = id.toHexString(),
    username = username,
    reference = reference,
    firstName = firstName,
    lastName = lastName,
    phone = phone,
    birthday = birthday,
    classroom = classroom.response(),
    imageUrl = imageUrl,
    genre = genre,
    roles = roles.map { it.response() },
    email = email,
    emailVerify = emailVerify,
    verify = verify,
    isNotLocked = isNotLocked
)

fun Student.contactResponse(): ContactResponse = ContactResponse(
    id = id.toHexString(),
    firstName = firstName,
    lastName = lastName,
    type = "Student",
    image = imageUrl
)

fun Role.response(): RoleResponse = RoleResponse(
    id = id.toHexString(),
    name = name
)

fun Professor.response(): ProfessorResponse = ProfessorResponse(
    id = id.toHexString(),
    firstName = firstName,
    lastName = lastName,
    username = username,
    email = email,
    phone = phone,
    avatar = avatar,
    roles = roles.map { it.response() },
    emailVerify = emailVerify,
    verify = verify,
    isNotLocked = isNotLocked,
    classrooms = classrooms.map { it.response() },
    createdAt = createdAt.toString(),
    genre = genre,
    updatedAt = updatedAt.toString()
)

fun Professor.contactResponse(): ContactResponse = ContactResponse(
    id = id.toHexString(),
    firstName = firstName,
    lastName = lastName,
    type = "Professor",
    image = avatar
)

fun Response.response(): ResponseResponse = ResponseResponse(
    id = id.toHexString(),
    description = description,
    professor = professor?.response(),
    student = student?.response(),
    createdAt = createdAt,
    solution = solution,
    forum = forum.response(),
    updatedAt = updatedAt
)

fun Forum.response(): ForumResponse = ForumResponse(
    id = id.toHexString(),
    description = description,
    professor = professor?.response(),
    student = student?.response(),
    createdAt = createdAt,
    views = views,
    title = title,
    classroom = classroom.response(),
    responses = responses,
    solve = solve
)

fun Message.response(): MessageResponse = MessageResponse(
    id = id.toHexString(),
    text = text,
    imageUrl = imageUrl,
    chatId = chatId,
    sender = sender,
    receiver = receiver,
    createdAt = createdAt
)

fun Lesson.response(): LessonResponse = LessonResponse(
    id = id.toHexString(),
    name = name,
    description = description,
    professor = professor.response(),
    classroom = classroom.response(),
    reference = reference,
    createdAt = createdAt,
    files = files,
    cover = cover,
    updatedAt = updatedAt,
    subject = subject
)

fun Post.response(): PostResponse = PostResponse(
    id = id.toHexString(),
    title = title,
    introduction = introduction,
    content = content,
    image = image,
    createdAt = createdAt,
    view = view,
    category = category.response(),
    updatedAt = updatedAt,
    user = user.response(),
    school = school.response()
)

fun User.response(): UserResponse = UserResponse(
    id = id.toHexString(),
    username = username,
    email = email,
    phone = phone,
    verify = verify,
    emailVerify = emailVerify,
    roles = roles.map { it.response() },
    schools = schools.map { it.response() }
)

fun UserSchool.response(): UserSchoolResponse = UserSchoolResponse(
    id = id.toHexString(),
    school = school.response(),
    enable = enable
)

fun Conversation.response(): ConversationResponse = ConversationResponse(
    id = id.toHexString(),
    sender = sender,
    lastMessage = lastMessage,
    unreadCount = unreadCount,
    receiver = receiver,
    chatId = chatId,
    createdAt = createdAt,
    updatedAt = updatedAt
)
// Request

fun Professor.request(request: ProfessorRequest): Professor = this.copy(
    firstName = request.firstName!!,
    lastName = request.lastName!!,
    phone = request.phone!!,
    genre = request.genre!!,
    email = request.email!!,
    username = request.username!!,
    password = request.password!!,
)

fun Student.request(request: StudentRequest): Student = this.copy(
    firstName = request.firstName!!,
    lastName = request.lastName!!,
    phone = request.phone!!,
    birthday = request.birthday!!,
    genre = request.genre!!,
    email = request.email!!,
    username = request.username!!,
    password = request.password!!,
)

fun Forum.request(request: ForumRequest): Forum = this.copy(
    title = request.title!!,
    description = request.description!!,
)

fun Response.request(request: ForumResponseRequest): Response = this.copy(
    description = request.description!!
)

fun Communicate.request(request: CommunicateRequest): Communicate = this.copy(
    title = request.title!!,
    content = request.message!!,
    type = CommunicateType.valueOf(request.type!!.uppercase()),
)

fun Lesson.request(request: LessonRequest): Lesson = this.copy(
    name = request.name!!,
    description = request.description!!,
    subject = request.subject!!,
    reference = UUID.randomUUID().toString()
)
fun Lesson.request(request: UpdateLessonRequest): Lesson = this.copy(
    name = request.name!!,
    description = request.description!!,
    subject = request.subject!!
)