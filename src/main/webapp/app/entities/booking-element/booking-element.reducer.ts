import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IBookingElement, defaultValue } from 'app/shared/model/booking-element.model';

export const ACTION_TYPES = {
  FETCH_BOOKINGELEMENT_LIST: 'bookingElement/FETCH_BOOKINGELEMENT_LIST',
  FETCH_BOOKINGELEMENT: 'bookingElement/FETCH_BOOKINGELEMENT',
  CREATE_BOOKINGELEMENT: 'bookingElement/CREATE_BOOKINGELEMENT',
  UPDATE_BOOKINGELEMENT: 'bookingElement/UPDATE_BOOKINGELEMENT',
  PARTIAL_UPDATE_BOOKINGELEMENT: 'bookingElement/PARTIAL_UPDATE_BOOKINGELEMENT',
  DELETE_BOOKINGELEMENT: 'bookingElement/DELETE_BOOKINGELEMENT',
  RESET: 'bookingElement/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IBookingElement>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type BookingElementState = Readonly<typeof initialState>;

// Reducer

export default (state: BookingElementState = initialState, action): BookingElementState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_BOOKINGELEMENT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_BOOKINGELEMENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_BOOKINGELEMENT):
    case REQUEST(ACTION_TYPES.UPDATE_BOOKINGELEMENT):
    case REQUEST(ACTION_TYPES.DELETE_BOOKINGELEMENT):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_BOOKINGELEMENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_BOOKINGELEMENT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_BOOKINGELEMENT):
    case FAILURE(ACTION_TYPES.CREATE_BOOKINGELEMENT):
    case FAILURE(ACTION_TYPES.UPDATE_BOOKINGELEMENT):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_BOOKINGELEMENT):
    case FAILURE(ACTION_TYPES.DELETE_BOOKINGELEMENT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_BOOKINGELEMENT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_BOOKINGELEMENT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_BOOKINGELEMENT):
    case SUCCESS(ACTION_TYPES.UPDATE_BOOKINGELEMENT):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_BOOKINGELEMENT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_BOOKINGELEMENT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/booking-elements';

// Actions

export const getEntities: ICrudGetAllAction<IBookingElement> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_BOOKINGELEMENT_LIST,
  payload: axios.get<IBookingElement>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IBookingElement> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_BOOKINGELEMENT,
    payload: axios.get<IBookingElement>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IBookingElement> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_BOOKINGELEMENT,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IBookingElement> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_BOOKINGELEMENT,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IBookingElement> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_BOOKINGELEMENT,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IBookingElement> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_BOOKINGELEMENT,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
