import dayjs from 'dayjs';
import { IHousingTemplate } from 'app/shared/model/housing-template.model';

export interface IHousingClosure {
  id?: number;
  startDate?: string;
  endDate?: string;
  cause?: string | null;
  establishmentId?: IHousingTemplate | null;
}

export const defaultValue: Readonly<IHousingClosure> = {};
