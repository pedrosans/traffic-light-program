/*    
 * traffic-light-program uses evolutionary computing to control traffic lights
 * Copyright (C) 2011  Pedro Henrique Oliveira dos Santos
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package tcc.model;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.mapper.MapperWrapper;

public class XStreamFacade {
	public static XStream xStream = new XStream() {
		protected MapperWrapper wrapMapper(MapperWrapper next) {
			return new MapperWrapper(next) {
				public boolean shouldSerializeMember(Class definedIn, String fieldName) {
					return definedIn != Object.class ? super.shouldSerializeMember(definedIn, fieldName) : false;
				}

			};
		}
	};
	static {
		xStream.setMode(XStream.NO_REFERENCES);
		xStream.processAnnotations(Net.class);
		xStream.processAnnotations(TLLogic.class);
		xStream.processAnnotations(TLLogic.Phase.class);
		xStream.processAnnotations(TripInfos.class);
		xStream.processAnnotations(TripInfos.TripInfo.class);
	}
}
