import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label, UncontrolledTooltip } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { IEstablishment } from 'app/shared/model/establishment.model';
import { getEntities as getEstablishments } from 'app/entities/establishment/establishment.reducer';
import { getEntity, updateEntity, createEntity, reset } from './booking.reducer';
import { IBooking } from 'app/shared/model/booking.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IBookingUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const BookingUpdate = (props: IBookingUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { bookingEntity, users, establishments, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/booking');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getUsers();
    props.getEstablishments();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...bookingEntity,
        ...values,
        jhiUserId: users.find(it => it.id.toString() === values.jhiUserIdId.toString()),
        establishmentId: establishments.find(it => it.id.toString() === values.establishmentIdId.toString()),
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="secretplacespaApp.booking.home.createOrEditLabel" data-cy="BookingCreateUpdateHeading">
            <Translate contentKey="secretplacespaApp.booking.home.createOrEditLabel">Create or edit a Booking</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : bookingEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="booking-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="booking-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="emailLabel" for="booking-email">
                  <Translate contentKey="secretplacespaApp.booking.email">Email</Translate>
                </Label>
                <AvField
                  id="booking-email"
                  data-cy="email"
                  type="text"
                  name="email"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="totalPriceLabel" for="booking-totalPrice">
                  <Translate contentKey="secretplacespaApp.booking.totalPrice">Total Price</Translate>
                </Label>
                <AvField
                  id="booking-totalPrice"
                  data-cy="totalPrice"
                  type="string"
                  className="form-control"
                  name="totalPrice"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
                <UncontrolledTooltip target="totalPriceLabel">
                  <Translate contentKey="secretplacespaApp.booking.help.totalPrice" />
                </UncontrolledTooltip>
              </AvGroup>
              <AvGroup>
                <Label id="commentLabel" for="booking-comment">
                  <Translate contentKey="secretplacespaApp.booking.comment">Comment</Translate>
                </Label>
                <AvField
                  id="booking-comment"
                  data-cy="comment"
                  type="text"
                  name="comment"
                  validate={{
                    maxLength: { value: 500, errorMessage: translate('entity.validation.maxlength', { max: 500 }) },
                  }}
                />
              </AvGroup>
              <AvGroup check>
                <Label id="validateLabel">
                  <AvInput id="booking-validate" data-cy="validate" type="checkbox" className="form-check-input" name="validate" />
                  <Translate contentKey="secretplacespaApp.booking.validate">Validate</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label for="booking-jhiUserId">
                  <Translate contentKey="secretplacespaApp.booking.jhiUserId">Jhi User Id</Translate>
                </Label>
                <AvInput id="booking-jhiUserId" data-cy="jhiUserId" type="select" className="form-control" name="jhiUserIdId">
                  <option value="" key="0" />
                  {users
                    ? users.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="booking-establishmentId">
                  <Translate contentKey="secretplacespaApp.booking.establishmentId">Establishment Id</Translate>
                </Label>
                <AvInput
                  id="booking-establishmentId"
                  data-cy="establishmentId"
                  type="select"
                  className="form-control"
                  name="establishmentIdId"
                >
                  <option value="" key="0" />
                  {establishments
                    ? establishments.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/booking" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  users: storeState.userManagement.users,
  establishments: storeState.establishment.entities,
  bookingEntity: storeState.booking.entity,
  loading: storeState.booking.loading,
  updating: storeState.booking.updating,
  updateSuccess: storeState.booking.updateSuccess,
});

const mapDispatchToProps = {
  getUsers,
  getEstablishments,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(BookingUpdate);
