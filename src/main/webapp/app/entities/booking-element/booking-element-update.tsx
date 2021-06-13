import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label, UncontrolledTooltip } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IBooking } from 'app/shared/model/booking.model';
import { getEntities as getBookings } from 'app/entities/booking/booking.reducer';
import { IHousingTemplate } from 'app/shared/model/housing-template.model';
import { getEntities as getHousingTemplates } from 'app/entities/housing-template/housing-template.reducer';
import { getEntity, updateEntity, createEntity, reset } from './booking-element.reducer';
import { IBookingElement } from 'app/shared/model/booking-element.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IBookingElementUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const BookingElementUpdate = (props: IBookingElementUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { bookingElementEntity, bookings, housingTemplates, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/booking-element');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getBookings();
    props.getHousingTemplates();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.startDate = convertDateTimeToServer(values.startDate);
    values.endDate = convertDateTimeToServer(values.endDate);

    if (errors.length === 0) {
      const entity = {
        ...bookingElementEntity,
        ...values,
        bookingId: bookings.find(it => it.id.toString() === values.bookingIdId.toString()),
        housingTemplateId: housingTemplates.find(it => it.id.toString() === values.housingTemplateIdId.toString()),
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
          <h2 id="secretplacespaApp.bookingElement.home.createOrEditLabel" data-cy="BookingElementCreateUpdateHeading">
            <Translate contentKey="secretplacespaApp.bookingElement.home.createOrEditLabel">Create or edit a BookingElement</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : bookingElementEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="booking-element-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="booking-element-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="startDateLabel" for="booking-element-startDate">
                  <Translate contentKey="secretplacespaApp.bookingElement.startDate">Start Date</Translate>
                </Label>
                <AvInput
                  id="booking-element-startDate"
                  data-cy="startDate"
                  type="datetime-local"
                  className="form-control"
                  name="startDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.bookingElementEntity.startDate)}
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="endDateLabel" for="booking-element-endDate">
                  <Translate contentKey="secretplacespaApp.bookingElement.endDate">End Date</Translate>
                </Label>
                <AvInput
                  id="booking-element-endDate"
                  data-cy="endDate"
                  type="datetime-local"
                  className="form-control"
                  name="endDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.bookingElementEntity.endDate)}
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="elementPriceLabel" for="booking-element-elementPrice">
                  <Translate contentKey="secretplacespaApp.bookingElement.elementPrice">Element Price</Translate>
                </Label>
                <AvField
                  id="booking-element-elementPrice"
                  data-cy="elementPrice"
                  type="string"
                  className="form-control"
                  name="elementPrice"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
                <UncontrolledTooltip target="elementPriceLabel">
                  <Translate contentKey="secretplacespaApp.bookingElement.help.elementPrice" />
                </UncontrolledTooltip>
              </AvGroup>
              <AvGroup>
                <Label for="booking-element-bookingId">
                  <Translate contentKey="secretplacespaApp.bookingElement.bookingId">Booking Id</Translate>
                </Label>
                <AvInput id="booking-element-bookingId" data-cy="bookingId" type="select" className="form-control" name="bookingIdId">
                  <option value="" key="0" />
                  {bookings
                    ? bookings.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="booking-element-housingTemplateId">
                  <Translate contentKey="secretplacespaApp.bookingElement.housingTemplateId">Housing Template Id</Translate>
                </Label>
                <AvInput
                  id="booking-element-housingTemplateId"
                  data-cy="housingTemplateId"
                  type="select"
                  className="form-control"
                  name="housingTemplateIdId"
                >
                  <option value="" key="0" />
                  {housingTemplates
                    ? housingTemplates.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/booking-element" replace color="info">
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
  bookings: storeState.booking.entities,
  housingTemplates: storeState.housingTemplate.entities,
  bookingElementEntity: storeState.bookingElement.entity,
  loading: storeState.bookingElement.loading,
  updating: storeState.bookingElement.updating,
  updateSuccess: storeState.bookingElement.updateSuccess,
});

const mapDispatchToProps = {
  getBookings,
  getHousingTemplates,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(BookingElementUpdate);
