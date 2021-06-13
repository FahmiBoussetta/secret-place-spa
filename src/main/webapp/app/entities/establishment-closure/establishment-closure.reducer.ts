import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IEstablishmentClosure, defaultValue } from 'app/shared/model/establishment-closure.model';

export const ACTION_TYPES = {
  FETCH_ESTABLISHMENTCLOSURE_LIST: 'establishmentClosure/FETCH_ESTABLISHMENTCLOSURE_LIST',
  FETCH_ESTABLISHMENTCLOSURE: 'establishmentClosure/FETCH_ESTABLISHMENTCLOSURE',
  CREATE_ESTABLISHMENTCLOSURE: 'establishmentClosure/CREATE_ESTABLISHMENTCLOSURE',
  UPDATE_ESTABLISHMENTCLOSURE: 'establishmentClosure/UPDATE_ESTABLISHMENTCLOSURE',
  PARTIAL_UPDATE_ESTABLISHMENTCLOSURE: 'establishmentClosure/PARTIAL_UPDATE_ESTABLISHMENTCLOSURE',
  DELETE_ESTABLISHMENTCLOSURE: 'establishmentClosure/DELETE_ESTABLISHMENTCLOSURE',
  RESET: 'establishmentClosure/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IEstablishmentClosure>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type EstablishmentClosureState = Readonly<typeof initialState>;

// Reducer

export default (state: EstablishmentClosureState = initialState, action): EstablishmentClosureState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ESTABLISHMENTCLOSURE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ESTABLISHMENTCLOSURE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_ESTABLISHMENTCLOSURE):
    case REQUEST(ACTION_TYPES.UPDATE_ESTABLISHMENTCLOSURE):
    case REQUEST(ACTION_TYPES.DELETE_ESTABLISHMENTCLOSURE):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_ESTABLISHMENTCLOSURE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_ESTABLISHMENTCLOSURE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ESTABLISHMENTCLOSURE):
    case FAILURE(ACTION_TYPES.CREATE_ESTABLISHMENTCLOSURE):
    case FAILURE(ACTION_TYPES.UPDATE_ESTABLISHMENTCLOSURE):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_ESTABLISHMENTCLOSURE):
    case FAILURE(ACTION_TYPES.DELETE_ESTABLISHMENTCLOSURE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_ESTABLISHMENTCLOSURE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_ESTABLISHMENTCLOSURE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_ESTABLISHMENTCLOSURE):
    case SUCCESS(ACTION_TYPES.UPDATE_ESTABLISHMENTCLOSURE):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_ESTABLISHMENTCLOSURE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_ESTABLISHMENTCLOSURE):
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

const apiUrl = 'api/establishment-closures';

// Actions

export const getEntities: ICrudGetAllAction<IEstablishmentClosure> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_ESTABLISHMENTCLOSURE_LIST,
  payload: axios.get<IEstablishmentClosure>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IEstablishmentClosure> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ESTABLISHMENTCLOSURE,
    payload: axios.get<IEstablishmentClosure>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IEstablishmentClosure> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ESTABLISHMENTCLOSURE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IEstablishmentClosure> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ESTABLISHMENTCLOSURE,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IEstablishmentClosure> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_ESTABLISHMENTCLOSURE,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IEstablishmentClosure> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ESTABLISHMENTCLOSURE,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
