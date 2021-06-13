import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IHousingTemplate, defaultValue } from 'app/shared/model/housing-template.model';

export const ACTION_TYPES = {
  FETCH_HOUSINGTEMPLATE_LIST: 'housingTemplate/FETCH_HOUSINGTEMPLATE_LIST',
  FETCH_HOUSINGTEMPLATE: 'housingTemplate/FETCH_HOUSINGTEMPLATE',
  CREATE_HOUSINGTEMPLATE: 'housingTemplate/CREATE_HOUSINGTEMPLATE',
  UPDATE_HOUSINGTEMPLATE: 'housingTemplate/UPDATE_HOUSINGTEMPLATE',
  PARTIAL_UPDATE_HOUSINGTEMPLATE: 'housingTemplate/PARTIAL_UPDATE_HOUSINGTEMPLATE',
  DELETE_HOUSINGTEMPLATE: 'housingTemplate/DELETE_HOUSINGTEMPLATE',
  RESET: 'housingTemplate/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IHousingTemplate>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type HousingTemplateState = Readonly<typeof initialState>;

// Reducer

export default (state: HousingTemplateState = initialState, action): HousingTemplateState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_HOUSINGTEMPLATE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_HOUSINGTEMPLATE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_HOUSINGTEMPLATE):
    case REQUEST(ACTION_TYPES.UPDATE_HOUSINGTEMPLATE):
    case REQUEST(ACTION_TYPES.DELETE_HOUSINGTEMPLATE):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_HOUSINGTEMPLATE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_HOUSINGTEMPLATE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_HOUSINGTEMPLATE):
    case FAILURE(ACTION_TYPES.CREATE_HOUSINGTEMPLATE):
    case FAILURE(ACTION_TYPES.UPDATE_HOUSINGTEMPLATE):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_HOUSINGTEMPLATE):
    case FAILURE(ACTION_TYPES.DELETE_HOUSINGTEMPLATE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_HOUSINGTEMPLATE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_HOUSINGTEMPLATE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_HOUSINGTEMPLATE):
    case SUCCESS(ACTION_TYPES.UPDATE_HOUSINGTEMPLATE):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_HOUSINGTEMPLATE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_HOUSINGTEMPLATE):
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

const apiUrl = 'api/housing-templates';

// Actions

export const getEntities: ICrudGetAllAction<IHousingTemplate> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_HOUSINGTEMPLATE_LIST,
  payload: axios.get<IHousingTemplate>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IHousingTemplate> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_HOUSINGTEMPLATE,
    payload: axios.get<IHousingTemplate>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IHousingTemplate> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_HOUSINGTEMPLATE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IHousingTemplate> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_HOUSINGTEMPLATE,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IHousingTemplate> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_HOUSINGTEMPLATE,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IHousingTemplate> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_HOUSINGTEMPLATE,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
