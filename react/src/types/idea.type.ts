export default interface IdeaData {
  ideaId?: string | any,
  userId?: number | any,
  timestamp: number,
  subject: string,
  content: string,
  attachment: string,
  allowedRoles: any
}