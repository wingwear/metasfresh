package de.metas.material.dispo.commons.candidate;

import javax.annotation.Nullable;

import org.adempiere.util.Check;

import de.metas.material.dispo.model.I_MD_Candidate_Demand_Detail;
import de.metas.material.event.commons.DocumentLineDescriptor;
import de.metas.material.event.commons.OrderLineDescriptor;
import de.metas.material.event.commons.SubscriptionLineDescriptor;
import de.metas.material.event.commons.SupplyRequiredDescriptor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * metasfresh-material-dispo
 * %%
 * Copyright (C) 2017 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */
@Value
@Builder
public class DemandDetail
{
	public static DemandDetail forDocumentDescriptor(
			final int shipmentScheduleId,
			@NonNull final DocumentLineDescriptor documentDescriptor)
	{
		final int orderId;
		final int orderLineId;
		final int subscriptionProgressId;
		if (documentDescriptor instanceof OrderLineDescriptor)
		{
			final OrderLineDescriptor orderLineDescriptor = (OrderLineDescriptor)documentDescriptor;
			orderLineId = orderLineDescriptor.getOrderLineId();
			orderId = orderLineDescriptor.getOrderId();
			subscriptionProgressId = -1;
		}
		else if (documentDescriptor instanceof SubscriptionLineDescriptor)
		{
			orderLineId = 0;
			orderId = 0;
			subscriptionProgressId = ((SubscriptionLineDescriptor)documentDescriptor).getSubscriptionProgressId();
		}
		else
		{
			Check.errorIf(true,
					"The given documentDescriptor has an unexpected type; documentDescriptor={}", documentDescriptor);
			return null;
		}

		return DemandDetail.builder()
				.shipmentScheduleId(shipmentScheduleId)
				.orderLineId(orderLineId)
				.orderId(orderId)
				.subscriptionProgressId(subscriptionProgressId).build();
	}

	public static DemandDetail createOrNull(
			@Nullable final SupplyRequiredDescriptor supplyRequiredDescriptor)
	{
		if (supplyRequiredDescriptor == null)
		{
			return null;
		}
		return DemandDetail.builder()
				.forecastLineId(supplyRequiredDescriptor.getForecastLineId())
				.shipmentScheduleId(supplyRequiredDescriptor.getShipmentScheduleId())
				.orderLineId(supplyRequiredDescriptor.getOrderLineId())
				.subscriptionProgressId(supplyRequiredDescriptor.getSubscriptionProgressId()).build();
	}

	public static DemandDetail forDemandDetailRecord(
			@NonNull final I_MD_Candidate_Demand_Detail demandDetailRecord)
	{
		return DemandDetail.builder()
				.forecastLineId(demandDetailRecord.getM_ForecastLine_ID())
				.shipmentScheduleId(demandDetailRecord.getM_ShipmentSchedule_ID())
				.orderLineId(demandDetailRecord.getC_OrderLine_ID())
				.subscriptionProgressId(demandDetailRecord.getC_SubscriptionProgress_ID()).build();
	}

	public static DemandDetail forShipmentScheduleIdAndOrderLineId(
			final int shipmentScheduleId,
			final int orderLineId,
			final int orderId)
	{
		return DemandDetail.builder()
				.shipmentScheduleId(shipmentScheduleId)
				.orderLineId(orderLineId)
				.orderId(orderId).build();
	}

	public static DemandDetail forForecastLineId(
			final int forecastLineId,
			final int forecastId)
	{
		return DemandDetail.builder()
				.forecastLineId(forecastLineId)
				.forecastId(forecastId).build();
	}

	int forecastId;

	int forecastLineId;

	int shipmentScheduleId;

	int orderId;

	int orderLineId;

	int subscriptionProgressId;

	private DemandDetail(
			final int forecastId,
			final int forecastLineId,
			final int shipmentScheduleId,
			final int orderId,
			final int orderLineId,
			final int subscriptionProgressId)
	{
		this.forecastId = forecastId;
		this.forecastLineId = forecastLineId;
		this.shipmentScheduleId = shipmentScheduleId;
		this.orderId = orderId;
		this.orderLineId = orderLineId;
		this.subscriptionProgressId = subscriptionProgressId;
	}
}
