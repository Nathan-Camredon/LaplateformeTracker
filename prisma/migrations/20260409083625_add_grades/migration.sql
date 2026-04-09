-- CreateTable
CREATE TABLE "User" (
    "login" TEXT NOT NULL,
    "password" TEXT NOT NULL
);

-- CreateTable
CREATE TABLE "Grade" (
    "id" SERIAL NOT NULL,
    "value" INTEGER NOT NULL,
    "subject" TEXT NOT NULL,
    "studentid" INTEGER NOT NULL,

    CONSTRAINT "Grade_pkey" PRIMARY KEY ("id")
);

-- CreateIndex
CREATE UNIQUE INDEX "User_login_key" ON "User"("login");

-- AddForeignKey
ALTER TABLE "Grade" ADD CONSTRAINT "Grade_studentid_fkey" FOREIGN KEY ("studentid") REFERENCES "Student"("id") ON DELETE RESTRICT ON UPDATE CASCADE;
