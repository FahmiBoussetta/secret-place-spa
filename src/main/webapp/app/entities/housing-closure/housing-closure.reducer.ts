import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IHousingClosure, defaultValue } from 'app/shared/model/housing-closure.model';

export const ACTION_TYPES = {
  FETCH_HOUSINGCLOSURE_LIST: 'housingClosure/FETCH_HOUSINGCLOSURE_LIST',
  FETCH_HOUSINGCLOSURE: 'housingClosure/FETCH_HOUSINGCLOSURE',
  CREATE_HOUSINGCLOSURE: 'housingClosure/CREATE_HOUSINGCLOSURE',
  UPDATE_HOUSINGCLOSURE: 'housingClosure/UPDATE_HOUSINGCLOSURE',
  PARTIAL_UPDATE_HOUSINGCLOSURE: 'housingClosure/PARTIAL_UPDATE_HOUSINGCLOSURE',
  DELETE_HOUSINGCLOSURE: 'housingClosure/DELETE_HOUSINGCLOSURE',
  RESET: 'housingClosure/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IHousingClosure>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type HousingClosureState = Readonly<typeof initialState>;

// Reducer

export default (state: HousingClosureState = initialState, action): HousingClosureState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_HOUSINGCLOSURE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_HOUSINGCLOSURE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_HOUSINGCLOSURE):
    case REQUEST(ACTION_TYPES.UPDATE_HOUSINGCLOSURE):
    case REQUEST(ACTION_TYPES.DELETE_HOUSINGCLOSURE):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_HOUSINGCLOSURE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_HOUSINGCLOSURE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_HOUSINGCLOSURE):
    case FAILURE(ACTION_TYPES.CREATE_HOUSINGCLOSURE):
    case FAILURE(ACTION_TYPES.UPDATE_HOUSINGCLOSURE):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_HOUSINGCLOSURE):
    case FAILURE(ACTION_TYPES.DELETE_HOUSINGCLOSURE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_HOUSINGCLOSURE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_HOUSINGCLOSURE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_HOUSINGCLOSURE):
    case SUCCESS(ACTION_TYPES.UPDATE_HOUSINGCLOSURE):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_HOUSINGCLOSURE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_HOUSINGCLOSURE):
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

const apiUrl = 'api/housing-closures';

// Actions

export const getEntities: ICrudGetAllAction<IHousingClosure> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_HOUSINGCLOSURE_LIST,
  payload: axios.get<IHousingClosure>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IHousingClosure> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_HOUSINGCLOSURE,
    payload: axios.get<IHousingClosure>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IHousingClosure> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_HOUSINGCLOSURE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IHousingClosure> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_HOUSINGCLOSURE,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IHousingClosure> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_HOUSINGCLOSURE,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IHousingClosure> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_HOUSINGCLOSURE,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
