import { Component, OnInit, Input, OnChanges, SimpleChanges } from '@angular/core';
import { CulturalOfferLocation } from '../model/culutral-offer-location';
import {environment} from '../../environments/environment.prod'
import * as Mapboxgl from 'mapbox-gl';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Geocoder } from '../model/geocoder';

@Component({
  selector: 'app-map-item',
  templateUrl: './map-item.component.html',
  styleUrls: ['./map-item.component.scss']
})
export class MapItemComponent implements OnInit, OnChanges {

  @Input()
  culturalOffers: CulturalOfferLocation[];
  @Input()
  location: string;

  mapa: Mapboxgl.Map;
  markers: Mapboxgl.Marker[] = [];
  mapInitialized: boolean = false;

  constructor(private httpClient: HttpClient) {  }

  ngOnChanges(changes: SimpleChanges): void{
    if(this.mapInitialized){
      if(changes['culturalOffers']){
        this.removeMarkers();
        this.updateMarkers();
      }
      if(changes['location'])
        this.focusOnLocation();
    }
  }

  ngOnInit(): void {
      this.initializeMap();
      this.updateMarkers();
  }
  createMarkers() :void{

    this.culturalOffers.forEach(offer => {
      const marker = new Mapboxgl.Marker({
        draggable: false
      }).setLngLat([offer.location.longitude, offer.location.latitude])
        .setPopup(new Mapboxgl.Popup().setHTML(`<h1 style="background-color:powderblue;">${offer.name}</h1>`));

      this.markers.push(marker);
    });
  }
  initializeMap(): void{
    (Mapboxgl as any).accessToken = environment.mapboxKey;

    this.mapa = new Mapboxgl.Map({
      container: 'map-mapbox',
      style: 'mapbox://styles/mapbox/streets-v11',
      center: [20.426773,44.9979649],   //LNG, LAT
      zoom: 5.5
      });

    this.mapInitialized = true;
  }
  updateMarkers(): void{
    this.createMarkers();
    //add markers on the map
    this.markers.forEach((marker) =>  marker.addTo(this.mapa));
  }

  removeMarkers(): void{
    this.markers.forEach((marker) => marker.remove());
    this.markers = [];
  }
  focusOnLocation(){
    this.location = this.capitalize(this.location);
    let api_url = `https://api.mapbox.com/geocoding/v5/mapbox.places/
                    ${this.location}.json?access_token=${environment.mapboxKey}`;

    let response = this.httpClient.get<Geocoder>(api_url);

    response.subscribe(geo => {
      if(geo.features[0]){
        this.mapa.setCenter(geo.features[0].bbox.slice(0,2) as [number, number]);
        this.mapa.setZoom(8);
      }
    });
  }

  capitalize = (s: string) => {
    return s.charAt(0).toUpperCase() + s.slice(1)
  }

}
