import dayjs from 'dayjs';
import { IEstablishment } from 'app/shared/model/establishment.model';

export interface IEstablishmentClosure {
  id?: number;
  startDate?: string;
  endDate?: string;
  cause?: string | null;
  establishmentId?: IEstablishment | null;
}

export const defaultValue: Readonly<IEstablishmentClosure> = {};
