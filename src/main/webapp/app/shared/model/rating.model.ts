import dayjs from 'dayjs';
import { IUser } from 'app/shared/model/user.model';
import { IEstablishment } from 'app/shared/model/establishment.model';

export interface IRating {
  id?: number;
  rate?: number;
  comment?: string | null;
  ratingDate?: string;
  userId?: IUser | null;
  establishmentId?: IEstablishment | null;
}

export const defaultValue: Readonly<IRating> = {};
