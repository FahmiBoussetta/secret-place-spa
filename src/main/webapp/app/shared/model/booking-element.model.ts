import dayjs from 'dayjs';
import { IBooking } from 'app/shared/model/booking.model';
import { IHousingTemplate } from 'app/shared/model/housing-template.model';

export interface IBookingElement {
  id?: number;
  startDate?: string;
  endDate?: string;
  elementPrice?: number;
  bookingId?: IBooking | null;
  housingTemplateId?: IHousingTemplate | null;
}

export const defaultValue: Readonly<IBookingElement> = {};
